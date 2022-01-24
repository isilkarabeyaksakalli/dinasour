/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package dinasour;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        Logger logger=LogManager.getLogger(App.class);
        logger.error("hello world");

        //Spark default olarak 4567.porttan çalışır. Heroku ise kendisi sanal makineden bir port seçecek. Bu yüzden port ayarlamamız gerekiyor.
         int port=Integer.parseInt(System.getenv("PORT"));
        port(port);
        logger.error("Current port number: "+port);
        



        System.out.println(new App().getGreeting());

       get("/", (req,res)-> "Hello, world!!");

       get("/compute",
           (rq,rs) -> {
               Map<String,String> map =new HashMap<String, String>();
               map.put("result", "not computed yet!");
               return new ModelAndView(map,"compute.mustache");
           },
           new MustacheTemplateEngine()
           );
        post("/compute", (req,res)->{
            String input1=req.queryParams("input1");
            java.util.Scanner sc1=new java.util.Scanner(input1);
            sc1.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Integer> inputList=new java.util.ArrayList<>();
            while(sc1.hasNext())
            {
                int value=Integer.parseInt(sc1.next().replaceAll("\\s", ""));
                inputList.add(value);
            }
            sc1.close();
            System.out.println(inputList);

            String input2=req.queryParams("input2").replaceAll("\\s", "");
            int input2AsInt= Integer.parseInt(input2);
            boolean result=App.search(inputList,input2AsInt);

            Map<String,Boolean> map =new HashMap<String, Boolean>();
            map.put("result", result);
            return new ModelAndView(map,"compute.mustache");
        },
        new MustacheTemplateEngine()
        );



    }
    public static boolean search (ArrayList<Integer> array, int e)
    {
        System.out.println("Inside search");
        if(array==null) return false;
        for(int elt: array){
            if(elt==e) return true;
        }
        return false;
    }
  
  
}
