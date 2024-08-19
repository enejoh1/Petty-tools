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
        String test = "7kP7SWi8P8Fs9erdK1CVp/S85Niq67Cw7V2efldTQ0nr098ICKU3M8zNFyWXYD+aw+ZA1EYNYjWFd2qUtYcPwh2WTiNgLcPc38I4q5UEUsO2Q945yTna7RVuiwkg/7GcpQTs5Kl7wDdf2heAToyJC12ZHBMudKECIjhKhxomDiyfgqjGGL7aZQgryNWWUUOQXt7zEi1vb6UUOqHPhmVrZsAXPP9lQEc+NtpyrOZVCnsjMo3Vfmh6b1CI9VJMA0I1Hjq0Bz9+TYbF4JYOsXHpWzMQC6JV/hXZlgynYFQ2JE31Vo7TpQzkDgUuPW63pQ9KX57xOGpxwGxTG4G3jKp2/+o+HIgQ/bJre21m7BLZ2LBcL61AFYv0VsB2UKKa5euwza1vxrCcb5wm10ZrM/TYVXkC1sysau5KBeA7XLykc3cCF/fAeT0Bb8++mDGMZXszTkzJPa7rghHZLWScwOP35/wdwfJ6g1AOxFlNgxVHtry95Snl2eBm7Y/ZADrD55Y84e9hi8UohrxCZ+P2eKICkgnoNT/KRKvzCug38D8n9zYzICpIqDaAt/OY/is+THS6QoD3GOepudmASpmFSjNmJTIxfD8SixASWz9P6qZKnIHjIzJ8HLvXbZ0dsZ+KMolLvm0jnRE05acgWwgwWfMnF9XUREIzoRnE2GnovZZnMtZQclWgX0jpr+0nChHQzMrYMxofnOBxeQXoTt8lIPeOpgdp+BCYvmM9p+UQgVRfraQ=";
        System.out.println();
        String decrypt = CoopvestUtil.decryptAES(test, CoopvestUtil.KEY, CoopvestUtil.IV);
        System.out.println(decrypt);

    }


}
