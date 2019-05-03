package kafka;

import java.io.*;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.ProducerPropertiesUtil;


public class KafkaCalllogProducer {
    public static void main(String[] args) {
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(ProducerPropertiesUtil.properties);
        final String Topic= "myTopic";
        try {

            String pathname = "/Users/joe/Desktop/BigData/project-08-bigdata_yq_hx/callLogs/logs.csv";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine();
                kafkaProducer.send(new ProducerRecord<String, String>(Topic,line));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        kafkaProducer.close();
    }


}
