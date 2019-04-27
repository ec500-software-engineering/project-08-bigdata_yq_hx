package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import utils.HBaseUtil;
import utils.ConsumerPropertiesUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HBaseDAO {
    private int regions;
    private String namespace;
    private String tableName;
    public static final Configuration conf;
    private Table table;
    private Connection connection;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");


    static {
        conf = HBaseConfiguration.create();
    }

    /*
        hbase.mytopic.regions = 6;
        hbase.mytopic.namespace = ns_mobile;
        hbase.mytopic.tablename = ns_mobile.calllogs;

     */

    public HBaseDAO() {
        try {
            regions = Integer.valueOf(ConsumerPropertiesUtil.getProperty("hbase.mytopic.regions"));
            namespace = ConsumerPropertiesUtil.getProperty("hbase.mytopic.namespace");
            tableName = ConsumerPropertiesUtil.getProperty("hbase.mytopic.tablename");

            connection = ConnectionFactory.createConnection(conf);
            table = connection.getTable(TableName.valueOf(tableName));


            if(!HBaseUtil.isExistTable(conf, tableName)) {
                HBaseUtil.initNamespace(conf, namespace);
                HBaseUtil.createTable(conf, tableName, regions, "f1", "f2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * ori data format： 18576581848,17269452013,2017-08-14 13:38:31,1761
     * rowkey：01_18576581848_20170814133831_17269452013_1_1761
     * HBase column：call1  call2   build_time   build_time_ts   flag   duration
     * @param ori
     */

    public void put(String ori) {
        try {

            String[] splitOri = ori.split(",");

            String caller = splitOri[0];
            String callee = splitOri[1];
            String buildTime = splitOri[2];
            String duration = splitOri[3];
            String regionCode = HBaseUtil.genRegionCode(caller, buildTime, regions);

            String buildTimeReplace = sdf2.format(sdf1.parse(buildTime));
            String buildTimeTs = String.valueOf(sdf1.parse(buildTime).getTime());

            //生成rowkey
            String rowKey = HBaseUtil.genRowKey(regionCode, caller, buildTimeReplace, callee, "1", duration);

            //向表中插入该条数据
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("call1"), Bytes.toBytes(caller));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("call2"), Bytes.toBytes(callee));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("build_time"), Bytes.toBytes(buildTime));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("build_time_ts"), Bytes.toBytes(buildTimeTs));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("flag"), Bytes.toBytes("1"));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("duration"), Bytes.toBytes(duration));

            table.put(put);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
