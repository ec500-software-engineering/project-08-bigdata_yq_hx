package hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import utils.HBaseUtil;
import utils.PropertiesUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalleeWriteObserver extends BaseRegionObserver {
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        String targetTableName = PropertiesUtil.getProperty("hbase.mytopic.tablename");
        String currentTableName = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();

        if(!targetTableName.equals(currentTableName)) {
            return;
        }

        String oriRowKey = Bytes.toString(put.getRow());

        String[] splitOriRowKey = oriRowKey.split("_");

        String oldFlag = splitOriRowKey[4];
        if(oldFlag.equals("0")) return;

        int regions = Integer.valueOf(PropertiesUtil.getProperty("hbase.mytopic.regions"));

        String caller = splitOriRowKey[1];
        String callee = splitOriRowKey[3];

        String buildTime = splitOriRowKey[2];
        String flag = "0";
        String duration = splitOriRowKey[5];
        String regionCode = HBaseUtil.genRegionCode(callee, buildTime, regions);
        String calleeRowKey = HBaseUtil.genRowKey(regionCode, callee, buildTime, caller, flag, duration);

        Put calleePut = new Put(Bytes.toBytes(calleeRowKey));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("call1"), Bytes.toBytes(callee));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("call2"), Bytes.toBytes(caller));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("build_time"), Bytes.toBytes(buildTime));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("flag"), Bytes.toBytes(flag));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("duration"), Bytes.toBytes(duration));
        try {
            calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("build_time_ts"),
                    Bytes.toBytes(new SimpleDateFormat("yyyyMMddHHmmss").parse(buildTime).getTime()));
        } catch (ParseException e1) {
            calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("build_time_ts"),
                    Bytes.toBytes(""));
            e1.printStackTrace();
        }

        Table table = e.getEnvironment().getTable(TableName.valueOf(targetTableName));
        table.put(calleePut);

        table.close();
    }
}
