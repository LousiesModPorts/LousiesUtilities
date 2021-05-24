package com.lousiesmods.lousiesutilities.handler;

import com.lousiesmods.lousiesutilities.LousiesUtilities;
import com.lousiesmods.lousiesutilities.util.Reference;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class UpdateHandler implements Runnable
{
    private static boolean upToDate = false;
    private String latest = "";
    private PlayerEntity player;

    public UpdateHandler(PlayerEntity player)
    {
        this.player = player;
    }

    public void run()
    {
        InputStream in = null;
        String url = "raw.githubusercontent.com/LousiesModPorts/LousiesUtilities/master/src/main/resources/META-INF/mods.toml";

        checkConnection(url);
    }

    public boolean upToDate()
    {
        return upToDate;
    }

    public String getLatestVersion()
    {
        return latest;
    }

    private void checkConnection(String url)
    {
        String newUrl = "https://" + url;

        try
        {
            URL myURL = new URL(newUrl);
            URLConnection connection = myURL.openConnection();

            InputStream in = connection.getInputStream();

            try
            {
                latest = IOUtils.readLines(in).get(6).replaceAll("\"", "").replace("version=", "");
            }

            catch (IOException e)
            {
                LousiesUtilities.LOGGER.fatal("Unable to determine update!");
                e.printStackTrace();
            }

            finally
            {
                IOUtils.closeQuietly(in);
            }
        }

        catch (MalformedURLException e)
        {
            LousiesUtilities.LOGGER.fatal("Unable to check for updates!");
            e.printStackTrace();
        }

        catch (IOException e)
        {
            LousiesUtilities.LOGGER.fatal("Unable to check for updates!");
            e.printStackTrace();
            //todo translations
        }

        upToDate = Reference.VERSION.equals(latest);

        if (!upToDate)
        {
            player.sendMessage(new TranslationTextComponent("messages.lousiesutilities.update"), null);
            //todo Download URL
        }
    }
}
