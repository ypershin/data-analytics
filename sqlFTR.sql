drop table FtrNodes;

create table FtrNodes (
  name varchar(40),
  pNodeId int primary key
);

bulk insert FtrNodes 
from 'C:\Users\ypershin\Downloads\ftr-source-sink-prompt-Feb16.csv'
with (
	firstrow=3,
	fieldterminator = ',',
	rowterminator = '\n',
	errorfile = 'C:\Users\ypershin\Downloads\errorFtrNodes.csv',
	tablock
);

select top 10 * from FtrNodes
order by pNodeId;

-- drop index iFtrNodes on FtrNodes;
-- create nonclustered index iFtrNodes on FtrNodes(pNodeId);


drop table NodeZone;
create table NodeZone (
  pNodeId int primary key,
  nodeName varchar(50),
  zoneName varchar(20),
  zoneType varchar(20) 
);


bulk insert NodeZone
from 'C:\Users\ypershin\Documents\ML\node-zone.csv'
with (
	firstrow=2,
	fieldterminator = ',',
	rowterminator = '\n',
	errorfile = 'C:\Users\ypershin\Documents\errorRows.csv',
	tablock
);

select top 10 * from NodeZone;

drop table FtrMcp;
select * into FtrMcp from (
select nodeId, month_, 
        sum(case when shapeId=1 then price else 0 end) prcPk,
        sum(case when shapeId=2 then price else 0 end) prcOpk
        from (
        select nodeId, effectiveDate month_, shapeId, period, price 
        from FtrClearingPrice with (nolock)
        where effectiveDate>='01/01/2011'
        and choose(month(effectiveDate),'JAN','FEB','MAR','APR','MAY','JUN','JUL',
                      'AUG','SEP','OCT','NOV','DEC')=period) a
        group by nodeId, month_
) z;

-- select top 10 * from FtrMcp;


create nonclustered index iFtrMcp on FtrMcp(month_) include(nodeId,prcPk,prcOpk);

drop table PriceMonthly2011;
drop table PriceMonthly2012;
drop table PriceMonthly2013;
drop table PriceMonthly2014;
drop table PriceMonthly2015;
drop table PriceMonthly2016;


create table PriceMonthly2011 (
  nodeId int,
  period date,
  revDaPk decimal(9,2),
  revDaOpk decimal(9,2),
  revRtPk decimal(9,2),
  revRtOpk decimal(9,2),
);

select * into PriceMonthly2012 from (select * from PriceMonthly2011 where 1=0) z;
select * into PriceMonthly2013 from (select * from PriceMonthly2011 where 1=0) z;
select * into PriceMonthly2014 from (select * from PriceMonthly2011 where 1=0) z;
select * into PriceMonthly2015 from (select * from PriceMonthly2011 where 1=0) z;
select * into PriceMonthly2016 from (select * from PriceMonthly2011 where 1=0) z;


bulk insert PriceMonthly2015
from 'C:\Users\ypershin\Documents\ML\FTR\prc2015.csv'
with (
	firstrow=2,
	fieldterminator = ',',
	rowterminator = '\n',
	errorfile = 'C:\Users\ypershin\Documents\ML\FTR\errorRows.csv',
	tablock
);


drop table PriceMonthly;
drop index iPrcMonthlyNode on PriceMonthly;

select * into PriceMonthly from (
  select pNodeId,period,revDaPk,revDaOpk,revRtPk,revRtOpk from (
  select * from PriceMonthly2011
  union all
  select * from PriceMonthly2012
  union all
  select * from PriceMonthly2013
  union all
  select * from PriceMonthly2014
  union all
  select * from PriceMonthly2015
  ) x, Node n, FtrNodes fn
  where x.nodeId=n.nodeId
  and n.marketId=1
  and cast(n.ExternalNodeId as int)=fn.pNodeId
) z;

create nonclustered index iPrcMonthlyNode on PriceMonthly(pNodeId);

select count(distinct pNodeId) from PriceMonthly;

select top 10 * from PriceMonthly;

-- drop index iNode on Node;
-- create nonclustered index iNode on Node(nodeId);

-- select top 10 nodeId,cast(ExternalNodeId as int) pNodeId from Node where nodeId=1 and marketId=1;

-- 112 WILTON - 167 PLANO
select p1.period, (p2.revDaPk-p1.revDaPk) rev
from PriceMonthly p1, PriceMonthly p2, NodeZone n1, NodeZone n2
where p1.pNodeId=n1.pNodeId and p2.pNodeId=n2.pNodeId
and n1.nodeName='112 WILTON' and n2.nodeName='167 PLANO'
and p1.period=p2.period
order by p1.period;


drop table SummaryData;

select * into SummaryData from (
select a.pNodeId srcId, b.pNodeId sinkId, 
count(a.period) cnt,
round(avg(b.revDaPk-a.revDaPk),2) avgPk, 
min(b.revDaPk-a.revDaPk) minPk,
max(b.revDaPk-a.revDaPk) maxPk,
round(stdev(b.revDaOpk-a.revDaOpk),2) sdPk,
round(avg(b.revDaOpk-a.revDaOpk),2) avgOpk, 
min(b.revDaOpk-a.revDaOpk) minOpk,
max(b.revDaOpk-a.revDaOpk) maxOpk,
round(stdev(b.revDaOpk-a.revDaOpk),2) sdOpk
from PriceMonthly a, PriceMonthly b
where a.period=b.period
and a.pNodeId<>b.pNodeId
-- and a.pNodeId<48650 and b.pNodeId<48650
group by a.pNodeId,b.pNodeId) z;

create nonclustered index iSummaryDataSrcSink on SummaryData(srcId,sinkId);



drop table CurrMonthMcp;

select * into CurrMonthMcp from (
  select cast(n1.externalNodeId as int) srcId, cast(n2.externalNodeId as int) sinkId, f1.month_ period,
  (f2.prcPk-f1.prcPk) mcpPk, (f2.prcOpk-f1.prcOpk) mcpOpk
  from FtrMcp f1, FtrMcp f2, Node n1, Node n2
  where n1.marketId=1 and n2.marketId=1
  and n1.nodeId=f1.nodeId and n2.nodeId=f2.nodeId
  and f1.month_=f2.month_
  and f1.month_='2016-01-01'
  -- and cast(n1.externalNodeId as int)<48650 and cast(n2.externalNodeId as int)<48650
) z;


-- select * from CurrMonthMcp;
create nonclustered index iCurrMonthMcp on CurrMonthMcp(srcId,sinkId);




drop table CurrMonthRev;

select * into CurrMonthRev from (
  select cast(n1.externalNodeId as int) srcId, cast(n2.externalNodeId as int) sinkId, f1.period,
  (f2.revDaPk-f1.revDaPk) revPk, (f2.revDaOpk-f1.revDaOpk) revOpk
  from PriceMonthly2016 f1, PriceMonthly2016 f2, Node n1, Node n2
  where n1.marketId=1 and n2.marketId=1
  and n1.nodeId=f1.nodeId and n2.nodeId=f2.nodeId
  and f1.period=f2.period
  -- and cast(n1.externalNodeId as int)<48650 and cast(n2.externalNodeId as int)<48650
) z;

create nonclustered index iCurrMonthRev on CurrMonthRev(srcId,sinkId);


-- select * from SummaryData;

select s.srcId,s.sinkId,n1.nodeName,n1.zoneName,n2.nodeName,n2.zoneName,
avgPk,minPk,maxPk,sdPk, round(avgPk/sdPk,2) shPk, mcpPk, revPk
from SummaryData s, NodeZone n1, NodeZone n2, CurrMonthMcp c, CurrMonthRev r
where n1.pNodeId=s.srcId and n2.pNodeId=s.sinkId
and c.srcId=s.srcId and c.sinkId=s.sinkId
and r.srcId=s.srcId and r.sinkId=s.sinkId;



