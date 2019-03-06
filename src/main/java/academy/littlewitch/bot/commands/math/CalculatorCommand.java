package academy.littlewitch.bot.commands.math;

import academy.littlewitch.bot.config.Configuration;
import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.StringWriter;
import java.util.ArrayList;

public class CalculatorCommand implements EverywhereCommand {

    public ScriptEngineManager mgr;
    public ScriptEngine jsEngine;

    public CalculatorCommand() {
        mgr = new ScriptEngineManager();
        jsEngine = mgr.getEngineByName("JavaScript");
    }

    @Override
    public String run(EventMessage eventMessage, User user, String s, ArrayList<String> arrayList) {
        if (!Configuration.config.globalCommandConfig.calculatorCommand.enabled) {
            return null;
        }
        if (arrayList.size() == 0)
            return null;
        String m = String.join(" ", arrayList);
        System.out.println(m);
        try {
            return jsEngine.eval(m).toString();
        } catch (ScriptException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CommandProperties properties() {
        return Configuration.config.globalCommandConfig.calculatorCommand.commandProperties;
    }
}
