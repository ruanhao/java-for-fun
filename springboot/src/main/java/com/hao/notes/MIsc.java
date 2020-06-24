package com.hao.notes;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;

import lombok.SneakyThrows;

public class MIsc {


    public static String obscurePassword(String cmd) {
        String result = cmd;
        result = Pattern.compile("(.*pass:)[^\\s-]*(.*)").matcher(result).replaceAll("$1********$2");
        // can add more filter here
        return result;
    }

    @Test
    @SneakyThrows
    public void test() {

        String pattern = "(.*state.*header.*ap-rogue-stats.*)";
        String sentence = "{\"state\":{\"header\":{\"system-name\":\"A2-TEST-CBD\",\"management-ip\":\"192.168.1.125\",\"version\":\"10.0.251.27\",\"serial-number\":\"PSZ23301ET4\",\"timestamp\":\"1587467906\",\"channel-type\":\"State\",\"channel-name\":\"ap-rogue-stats\",\"xpath\":\"/wlc/ops/system/ap-rogue-stats\",\"data-format\":\"bulk\",\"snapshot-seq\":\"256\",\"chunk-seq\":\"1\"},\"payload\": {\"wlc\": {\"ops\": {\"system\": {\"ap-rogue-stats\":{\"rogues\":[{\"mac\":\"70:6D:15:B7:57:ED\",\"state-type\":2,\"reporting-ap\":[{\"mac\":\"68:CA:E4:70:AD:00\",\"product-id\":\"CBW142ACM-B\",\n\"serial-number\":\"KWC2319047Z\",\"version-id\":\"V01\",\"reporting-ap-slot\":[{\"slot-id\":1,\"channel\":36,\"channel-width\":1,\"ssid\":\"SMB-Mgnt\",\"rssi\":-73,\"snr\":15,\"containment-type\":0,\"security-type\":6,\"radio-type\":5}]},{\"mac\":\"D4:78:9B:D6:78:60\",\"product-id\":\"CBW240AC-B\",\"serial-number\":\"PSZ23301ERM\",\"version-id\":\"V01\",\"reporting-ap-slot\":[{\"slot-id\":1,\"channel\":36,\"channel-width\":1,\"ssid\":\"SMB-Mgnt\",\"rssi\":-68,\"snr\":23,\"containment-type\":0,\"security-type\":6,\"radio-type\":5}]},{\"mac\":\"D4:78:9B:D6:7B:00\",\"product-id\":\"CBW240AC-B\",\"serial-number\":\"PSZ23301ET4\",\"version-id\":\"V01\",\"reporting-ap-slot\":[{\"slot-id\":1,\"channel\":36,\"channel-width\":1,\"ssid\":\"SMB-Mgnt\",\"rssi\":-64,\"snr\":28,\"containment-type\":0,\"security-type\":6,\"radio-type\":5}]}]},{\"mac\":\"70:6D:1";
        System.err.println(Pattern.matches(pattern, sentence));

        System.out.println(8 >> 2);

        Set<Person> set = new HashSet<>();
        set.add(new Person());

        Set<Person> set2 = new HashSet<>();
        set2.add(new Person());

        System.out.println(set.containsAll(set2));
    }

    public void testt(double i) {
        System.out.println(i);
    }
}


class Person {
    int age;

    @Override
    public int hashCode() {
        System.out.println("hashcode");
        return super.hashCode();
    }


}
