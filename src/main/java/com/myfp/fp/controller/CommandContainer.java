package com.myfp.fp.controller;

import com.myfp.fp.controller.tariffs.AddTariffsCommand;
import com.myfp.fp.controller.tariffs.DeleteTariffsCommand;
import com.myfp.fp.controller.tariffs.UpdateTariffsCommand;
import jakarta.servlet.ServletException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static Map<String, Class<? extends Command>> commands = new HashMap<>();
    static {
        commands.put("/", MainCommand.class);
        commands.put("/index", MainCommand.class);
        commands.put("/login", LoginCommand.class);
        commands.put("/logout", LogoutCommand.class);
        commands.put("/registration", RegistrationCommand.class);
        commands.put("/tariffs", TariffsCommand.class);
        commands.put("/tariffs/add", AddTariffsCommand.class);
        commands.put("/tariffs/update", UpdateTariffsCommand.class);
        commands.put("/tariffs/delete", DeleteTariffsCommand.class);
    }

    public static Command getCommand(String url) throws ServletException {
        Class<?> command = commands.get(url);
        if (command != null) {
            try {
                return (Command) command.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ex) {
                System.out.println("Get command exception!");
                ex.printStackTrace();
            }
        }
        return null;
    }
}
