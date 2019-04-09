package hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
<<<<<<< HEAD
=======
import org.apache.hadoop.hbase.client.HTableInterface;
>>>>>>> a53569da901192a3913657a928cd4e83c081ce94
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

<<<<<<< HEAD
public class CalleeWriteObserver extends BaseRegionObserver{
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability)
            throws IOException {
        super.postPut(e, put, edit, durability);
        //1、获取你想要操作的目标表的名称
        String targetTableName = PropertiesUtil.getProperty("hbase.mytopic.tablename");
        //2、获取当前成功Put了数据的表（不一定是我们当前业务想要操作的表）
        String currentTableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();

        if(!targetTableName.equals(currentTableName)) return;
=======
public class CalleeWriteObserver extends BaseRegionObserver {
    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        String targetTableName = PropertiesUtil.getProperty("hbase.mytopic.tablename");
        String currentTableName = e.getEnvironment().getRegion().getRegionInfo().getTable().getNameAsString();

        if(!targetTableName.equals(currentTableName)) {
            return;
        }
>>>>>>> a53569da901192a3913657a928cd4e83c081ce94

        String oriRowKey = Bytes.toString(put.getRow());

        String[] splitOriRowKey = oriRowKey.split("_");

        String oldFlag = splitOriRowKey[4];
<<<<<<< HEAD
        //如果当前插入的是被叫数据，则直接返回(因为默认提供的数据全部为主叫数据)
=======
>>>>>>> a53569da901192a3913657a928cd4e83c081ce94
        if(oldFlag.equals("0")) return;

        int regions = Integer.valueOf(PropertiesUtil.getProperty("hbase.mytopic.regions"));

        String caller = splitOriRowKey[1];
        String callee = splitOriRowKey[3];
<<<<<<< HEAD
=======

>>>>>>> a53569da901192a3913657a928cd4e83c081ce94
        String buildTime = splitOriRowKey[2];
        String flag = "0";
        String duration = splitOriRowKey[5];
        String regionCode = HBaseUtil.genRegionCode(callee, buildTime, regions);
<<<<<<< HEAD

        String calleeRowKey = HBaseUtil.genRowKey(regionCode, callee, buildTime, caller, flag, duration);
        //生成时间戳
        String buildTimeTs = "";
        try {
            buildTimeTs = String.valueOf(sdf.parse(buildTime).getTime());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        // call1 call2 build_time build_time_ts
=======
        String calleeRowKey = HBaseUtil.genRowKey(regionCode, callee, buildTime, caller, flag, duration);

>>>>>>> a53569da901192a3913657a928cd4e83c081ce94
        Put calleePut = new Put(Bytes.toBytes(calleeRowKey));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("call1"), Bytes.toBytes(callee));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("call2"), Bytes.toBytes(caller));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("build_time"), Bytes.toBytes(buildTime));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("flag"), Bytes.toBytes(flag));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("duration"), Bytes.toBytes(duration));
<<<<<<< HEAD
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("build_time_ts"), Bytes.toBytes(buildTimeTs));
        Bytes.toBytes(100L);

        Table table = e.getEnvironment().getTable(TableName.valueOf(targetTableName));
        table.put(calleePut);
        table.close();
    }
}

=======
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
>>>>>>> a53569da901192a3913657a928cd4e83c081ce94
