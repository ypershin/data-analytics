drop table MonthlyData;

create table MonthlyData (
  nodeId int,
  period date,
  revDaPk decimal(9,2),
  revDaOpk decimal(9,2),
  revRtPk decimal(9,2),
  revRtOpk decimal(9,2),
  insertDate date
);


select * into CurrentMonthData from MonthlyData where 1=0;

bulk insert MonthlyData 
from 'C:\Users\me\Dropbox\Dynasty\prc\prc2015.csv'
with (
	firstrow=2,
	fieldterminator = ',',
	rowterminator = '\n',
	errorfile = 'C:\Users\me\Dropbox\Dynasty\prc\errorRows.csv',
	tablock
);


bulk insert CurrentMonthData 
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
-- and a.nodeId = 1
group by a.nodeId,b.nodeId) z;


drop table CurrSummaryData;
select * into CurrSummaryData from (
select a.nodeId srcId, b.nodeId sinkId, 
(b.revDaPk-a.revDaPk) currRev
from CurrentMonthData a, CurrentMonthData b
where a.nodeId<>b.nodeId
) z;


select top 10 * from CurrSummaryData;
create nonclustered index idxCurrPath on CurrSummaryData(srcId,sinkId);

create nonclustered index idxPath on SummaryData(srcId,sinkId);


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

drop index idxSdRev on SummaryData;
create nonclustered index idxRev on SummaryData (avgRev,sdRev,cnt) include (srcId,sinkId,minRev,maxRev);


select max(avgRev/sdRev) from SummaryData where avgRev>0 and sdRev>0;
select count(*) from SummaryData where (avgRev/sdRev)>1 and avgRev>0 and sdRev>0 and cnt>=24;

select @@Version
go
select * from sys.dm_os_process_memory
go

select top 10 * from vwSummary1
where (avgRev/sdRev)>1 and avgRev>0 and sdRev>0 and cnt>=24;;
