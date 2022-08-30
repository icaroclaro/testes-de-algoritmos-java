import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class TestAmazon {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\Probook\\Desktop\\saida.txt"));

        int logsCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> logs = IntStream.range(0, logsCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        int threshold = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> result = Result.processLogs(logs, threshold);

        bufferedWriter.write(
            result.stream()
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

class Result {

    /*
     * Complete the 'processLogs' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. STRING_ARRAY logs
     *  2. INTEGER threshold
     */

    public static List<String> processLogs(List<String> logs, int threshold) {
        Map<Integer, Integer> transacoesMap = new HashMap<Integer, Integer>();
        System.out.println("Threshold: " + threshold);
        for(String transacao : logs){
            System.out.println("Transacao: " + transacao);
            String[] transacaoArray = transacao.split(" ");
            Integer sender = Integer.parseInt(transacaoArray[0]);
            Integer receiver = Integer.parseInt(transacaoArray[1]);
            
            if(sender.equals(receiver)){
                transacoesMap.put(sender, transacoesMap.containsKey(sender) ? transacoesMap.get(sender) + 1 : 1);
            }else{
                transacoesMap.put(sender, transacoesMap.containsKey(sender) ? transacoesMap.get(sender) + 1 : 1);
                transacoesMap.put(receiver, transacoesMap.containsKey(receiver) ? transacoesMap.get(receiver) + 1 : 1);
            }
        }
        for(Map.Entry<Integer, Integer> set : transacoesMap.entrySet()){
            System.out.println("Key: " + set.getKey() + "Value: " + set.getValue());
        }
        
        Map<Integer, TreeMap<Integer, Integer>> treeMapKey = new TreeMap<Integer, TreeMap<Integer, Integer>>(Collections.reverseOrder());
        
        for(Map.Entry<Integer, Integer> set : transacoesMap.entrySet()){
            TreeMap<Integer, Integer> treeMapValue = new TreeMap<Integer, Integer>();
            if(set.getValue() >= threshold){
                if(treeMapKey.containsKey(set.getValue())){
                    TreeMap<Integer, Integer> tempMap = treeMapKey.get(set.getValue());
                    tempMap.put(set.getKey(), set.getValue());
                    treeMapKey.put(set.getValue(), tempMap);
                }else{
                    treeMapValue.put(set.getKey(), set.getValue());
                    treeMapKey.put(set.getValue(), treeMapValue);
                }
            }
        }
        System.out.println("Print 3: ");
        for(Map.Entry<Integer, TreeMap<Integer, Integer>> set : treeMapKey.entrySet()){
            TreeMap<Integer, Integer> values = set.getValue();
            for(Map.Entry<Integer, Integer> set2 : values.entrySet()){
                System.out.println("Key: " + set2.getKey() + " Value: " + set2.getValue());
            }
        }
        
        List<String> listReturn = new ArrayList<String>();
        
        for(Map.Entry<Integer, TreeMap<Integer, Integer>> set : treeMapKey.entrySet()){
            
            TreeMap<Integer, Integer> values = set.getValue();
            
            for(Map.Entry<Integer, Integer> set2 : values.entrySet()){
                listReturn.add(String.valueOf(set2.getKey()));
            }
        }
        return listReturn;
    }
}