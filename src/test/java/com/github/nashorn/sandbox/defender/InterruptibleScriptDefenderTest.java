package com.github.nashorn.sandbox.defender;

import org.junit.Before;
import org.junit.Test;

/**
 * 填写描述
 *
 * @author baez
 * @date 2020-01-06 13:47
 */
public class InterruptibleScriptDefenderTest {

    private String script;

    @Before
    public void setUp() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("var person = { name: 'foo', age: 23 }").append("\n")
                .append("with(person) {").append("\n")
                .append("   print('person name: ' + name)").append("\n")
                .append("   print('person age: ' + age)").append("\n")
                .append("}").append("\n")
                .append("while(true) {").append("\n")
                .append("   // do nothing").append("\n")
                .append("   continue").append("\n")
                .append("}").append("\n")
                .append("function hello() {").append("\n")
                .append("   do {").append("\n")
                .append("       var a = 1").append("\n")
                .append("       a = 2").append("\n")
                .append("       a = 3").append("\n")
                .append("       a = 4").append("\n")
                .append("       a = 5").append("\n")
                .append("       a = 6").append("\n")
                .append("       a = 7").append("\n")
                .append("       a = 8").append("\n")
                .append("       a = 9").append("\n")
                .append("       a = 10").append("\n")
                .append("       a = 11").append("\n")
                .append("       a = 12").append("\n")
                .append("       a = 13").append("\n")
                .append("       a = 14").append("\n")
                .append("       a = 15").append("\n")
                .append("       a = 16").append("\n")
                .append("       a = 17").append("\n")
                .append("   } while(true)").append("\n")
                .append("}").append("\n")
                .append("if(true) {").append("\n")
                .append("   for(var i = 0; i < 100; i++) {").append("\n")
                .append("       break").append("\n")
                .append("   }").append("\n")
                .append("} else if(1 > 2) {").append("\n")
                .append("   var a = 2").append("\n")
                .append("   switch(a) {").append("\n")
                .append("       case 1:").append("\n")
                .append("           a + 1").append("\n")
                .append("           break").append("\n")
                .append("       case 2:").append("\n")
                .append("           a - 1").append("\n")
                .append("           break").append("\n")
                .append("       case 3:").append("\n")
                .append("           a + 2").append("\n")
                .append("           break").append("\n")
                .append("       default:").append("\n")
                .append("           break").append("\n")
                .append("   }").append("\n")
                .append("} else {").append("\n")
                .append("}").append("\n")
                .append("while(1 > 2 || (3 < 2 && 44 / 2 > 2)) {var a = 1;}")
                .append("do   {  \n   var a = 1;} while((2 - 3 > 0));")
                .append("if ((1 + 3) > 3 || true) new Object()").append("\n")
                .append("for(;;){").append("\n")
                .append("for(;;){").append("\n")
                .append("for(;;){}}}").append("\n");
        script = builder.toString();
    }

    @Test
    public void testDefend() throws Exception {
        System.out.println(script);
        System.out.println("===========");
        InterruptibleScriptDefender defender = new InterruptibleScriptDefender();
        String muchSaferScript = defender.defend(script);
        System.out.println(muchSaferScript);
    }
}