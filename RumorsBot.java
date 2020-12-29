package Client;

import static Client.Constant.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class RumorsBot {

    private JDA api;

    public void buildJDA() {
        try {
            DiscordEventListener listener = new DiscordEventListener(this);
            api = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
            api.addEventListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JDA getJDA() {
        return api;
    }
}
