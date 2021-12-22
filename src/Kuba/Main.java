package Kuba;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String CSV_SEPARATOR =",";

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

/*
        Application creates new.CSV file from existing data.csv using world.sql database.
*/

        // 1.Creating list of <Obiekt>

        List<Obiekt> listaObiektow = new ArrayList<>();

        // 2.Adding data to listaObiektow from CSV

        addToListaObiektowFromCSV(listaObiektow);

        // 3.Creating list of <Codes>

        List<Codes> codes = new ArrayList<Codes>();

        // 4.Adding data to codes from MySQL

        addToCodesFromMySQL(codes);

        // 5.Setting "new" names in <Obiekt> from Codes

        codesToNames(listaObiektow,codes);

        // 6.Writing results into CSV file

        writeToCSV(listaObiektow);

        }

        private static void codesToNames(List<Obiekt> listaObiektow, List<Codes> codes){
            for (Obiekt z:listaObiektow) {
                for (Codes c:codes) {
                    if (c.getCode().equals(z.getKraj())) {
                    z.setKraj(c.getName());
                }
                }
            }
        }

        private static void writeToCSV(List<Obiekt> listaObiektow){
            try
            {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("new.csv"), StandardCharsets.UTF_8));
                for (Obiekt o : listaObiektow)
                {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(o.getKraj().trim().length() == 0? "" : o.getKraj());
                    stringBuffer.append(CSV_SEPARATOR);
                    stringBuffer.append(o.getRok());
                    stringBuffer.append(CSV_SEPARATOR);
                    stringBuffer.append(o.getWartosc());
                    bw.write(stringBuffer.toString());
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            } catch (IOException e){}
        }

        private static void addToListaObiektowFromCSV(List<Obiekt> listaObiektow) throws IOException {

            String file = "data.csv";
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(CSV_SEPARATOR);
                Obiekt o = new Obiekt(row[5],row[6],row[7]);
                listaObiektow.add(o);
            }
        }

        private static void addToCodesFromMySQL(List<Codes> codes) throws SQLException, ClassNotFoundException {

            String url = "jdbc:mysql://localhost:3306/world";
            String uname = "root";
            String pass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, uname, pass);

            String query = "SELECT * FROM country";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                Codes code = new Codes();
                code.setCode(result.getString("Code2"));
                code.setName(result.getString("Name"));
                codes.add(code);
            }
            con.close();
        }
}

