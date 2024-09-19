import cn.hutool.json.JSONUtil;

public class enc_dec {

    public static void main(String[] args) {


        String s = "{\n" +
                "    \"agentAmt\": \"19,900.00\",\n" +
                "    \"aggregatorAmt\": \"20.00\",\n" +
                "    \"createAt\": \"2024-07-26 17:58:28\",\n" +
                "    \"destAcct\": \"506106******5253\",\n" +
                "    \"fee\": \"100.00\",\n" +
                "    \"orderMsg\": \"SUCCESSFUL\",\n" +
                "    \"orderNo\": \"853254207151559850\",\n" +
                "    \"orderStatus\": \"00\",\n" +
                "    \"orderTypeId\": 2,\n" +
                "    \"origAmt\": \"20,000.00\",\n" +
                "    \"rrn\": \"100002469713\",\n" +
                "    \"sentAt\": \"2024-07-26 17:58:28\",\n" +
                "    \"serialNum\": \"T502310100000657\",\n" +
                "    \"tid\": \"HOR-00004\",\n" +
                "    \"transAmt\": \"19,900.00\",\n" +
                "    \"transName\": \"PURCHASE\",\n" +
                "    \"updatedAt\": \"2024-07-26 17:58:28\",\n" +
                "    \"userName\": \"08163904647\"\n" +
                "}";

        String s1 = JSONUtil.toJsonStr(s);

        String encryptAES = CoopvestUtil.encryptAES(s1, CoopvestUtil.KEY, CoopvestUtil.IV);
        System.out.println(encryptAES);

        //FhGHjdhS552fy2JqQhkxOK6IIKcvxAQngaaXzBWYiBL5MMl/YdAj+4nlEbF+IJRu2TNqpnPX9eVTzfof3wH9tqnbBsLnJ3t3qcFDTAjRUG2IVTz0az0Icm68limoRo/q2WB5876DzKRIk16+ueYKBuG6ednFO13fC9PQpu8aMCep8KBajcgbmQpBrxTyv9ZLBqQKD6PiNmUJSX8HLhB50YyX/MHODl8O22adapLxT2hcGsfG9b5v9kesxnRJ516dbRiF9uupUSDkeUVPtmaNiUvV4/jjJJkbcDbVQ3B0+Tzx0R5w3TSjTPk5DnaKuZ0hdlpAO1ZofGJn15a+bheLBulsxW0DVoljbG5AVhSxQR6KY9VRBnbJmyYzZlUTcAHBPqmp+pj7dRYY3VWidLO//Wa+5+dGgkUXMNeQzrsdVxhd/IhLgi16DLeKZuM9Rvj/i4JqPMWncKhRUS9pXNBWcY5v1BcyvZnVQAxf4dhfjL00syYGggT65x7W7Nrs6MMmul+g71sf3WvISIPZhzEDUJUiGsfsTNkBuhrM4vSfZdoJI6cFnR70RGppq68ngcW97aMa/Jofj5JbPyKUGpud/w==
        String test = "9nSAuDrLItbs12MvvfzLwXO5QhtYztxNPQf+7uXy85Lhe//3EJbRO27JsrNHF5lcLuppBZ5RTw0FyAjA9q/cJweCOMjvczLO8diE3iiq+kMVDQhwfUZ0fGjW2qArJi1vwLlz/IKJJnq6/y/qZTcS1D0uJnip15+Uz7QBYYYJkoe05H3qHLp2Twos1vYGTip9s/Sc+INRB53Gxe6W4m8BFEJ+X6+pysPX6/0UyeKdEysx5vWvFxNlDkP1R6ZgnK5jB86ZzU5V/yQxG/x7Ale/IkXgE/l4OuEG4MbHmhHenty/OC85RmQ59YpyYIxizR8uaLP1oaFCn8RlgnippBLRvXxfvvZxw5XoJKZxsKRuPk4HjSC2LHdPLbZcit2dp0hFfeWkZEvdPXGDMCvHjwWOtF/J4OgwpDpnuDD9x253WXfPOouXLUyfFXgBqXbi4J8h8CD8SVPqMt5T51UfIC0hMOr49S4h4pWUSmXd40YvPos=";
        System.out.println();
        String decrypt = CoopvestUtil.decryptAES(test, CoopvestUtil.KEY, CoopvestUtil.IV);
        System.out.println(s1);

    }


}
