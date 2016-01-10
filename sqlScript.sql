drop table MonthlyData;

create table MonthlyData (
  nodeId int,
  period date,
  revDaPk decimal(15,2),
  revDaOpk decimal(15,2),
  revRtPk decimal(15,2),
  revRtOpk decimal(15,2)
);


bulk insert MonthlyData 
from 'C:\Users\me\Dropbox\Dynasty\prc\prc2016.csv'
with (
	firstrow=2,
	fieldterminator = ',',
	rowterminator = '\n',
	errorfile = 'C:\Users\me\Dropbox\Dynasty\prc\errorRows.csv',
	tablock
);


-- select top 10 * from MonthlyData;

drop index idxMDperiod on MonthlyData;
create nonclustered index idxMDperiod on MonthlyData(period) include(nodeId,revDaPk);
create nonclustered index idxMDnodeid on MonthlyData(nodeId);



-- dbcc freeproccache
-- dbcc dropcleanbuffer

drop table SummaryData;

select * into SummaryData from (
select a.nodeId srcId, b.nodeId sinkId, 
count(a.period) cnt,
round(avg(b.revDaPk-a.revDaPk),2) avgRev, 
min(b.revDaPk-a.revDaPk) minRev,
max(b.revDaPk-a.revDaPk) maxRev,
round(stdev(b.revDaPk-a.revDaPk),2) sdRev
from MonthlyData a, MonthlyData b
where a.period=b.period
and a.nodeId<>b.nodeId
and a.nodeId = 1
group by a.nodeId,b.nodeId) z;



select * into Nodes from (
select distinct nodeId from MonthlyData
) z;

select top 10 * from Nodes
order by nodeId;

-- *********** PROC START ***********
declare @node int

declare db_cursor cursor for
select nodeId from Nodes where nodeId<>1;

open db_cursor
fetch next from db_cursor into @node

while @@FETCH_STATUS = 0
begin
	insert into SummaryData 
	select a.nodeId srcId, b.nodeId sinkId, 
	count(a.period) cnt,
	round(avg(b.revDaPk-a.revDaPk),2) avgRev, 
	min(b.revDaPk-a.revDaPk) minRev,
	max(b.revDaPk-a.revDaPk) maxRev,
	round(stdev(b.revDaPk-a.revDaPk),2) sdRev
	from MonthlyData a, MonthlyData b
	where a.period=b.period
	and a.nodeId<>b.nodeId
	and a.nodeId = @node
	group by a.nodeId,b.nodeId

	fetch next from db_cursor into @node;
end

close db_cursor
deallocate db_cursor
-- *********** PROC END ***********

select count(*) from SummaryData;

drop index idxSdRev on SummaryData100k;
create nonclustered index idxSdRev on SummaryData (avgRev,sdRev) include (srcId,sinkId,cnt,minRev,maxRev);

drop table SummaryData100k;
select * into SummaryData100k from (select top 10000000 * from SummaryData) z;

select count(*) from SummaryData100k where avgRev/sdRev>1 and avgRev>0;

select @@Version
go
select * from sys.dm_os_process_memory
go
