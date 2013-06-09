package net.iaeste.iws.core.ffmq;

import net.timewalker.ffmq3.FFMQCoreSettings;
import net.timewalker.ffmq3.FFMQException;
import net.timewalker.ffmq3.listeners.ClientListener;
import net.timewalker.ffmq3.listeners.tcp.io.TcpListener;
import net.timewalker.ffmq3.local.FFMQEngine;
import net.timewalker.ffmq3.management.destination.definition.QueueDefinition;
import net.timewalker.ffmq3.utils.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

/**
 * @author Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public class MessageServer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageServer.class);

    private FFMQEngine engine;
    private ClientListener tcpListener;

    private boolean deployed = false;
    private static final String queueName = "iws-EmailQueue";

    public static final String engineName = "IwsFfmqMessageServer";
    public static final String listenAddr = "0.0.0.0";
    public static final int listenPort = 10002;

    public void run() {
        try {
            System.setProperty("FFMQ_BASE", "..");

            final Settings settings = createEngineSettings();
            engine = new FFMQEngine(engineName, settings);

            LOG.trace("Starting listener");
            tcpListener = new TcpListener(engine, listenAddr, listenPort, settings, null);
            tcpListener.start();

            final QueueDefinition queueDef = new QueueDefinition();
            queueDef.setName(queueName);
            queueDef.setUseJournal(false);
            queueDef.setMaxNonPersistentMessages(1000);
            queueDef.check();

            if(engine.getDestinationDefinitionProvider().hasQueueDefinition(queueName)) {
                engine.getDestinationDefinitionProvider().removeQueueDefinition(queueDef);
            }

            engine.createQueue(queueDef);

            LOG.trace("Deploying engine " + engine.getName());
            engine.deploy();

            deployed = true;
            LOG.trace("Running");
        } catch (FFMQException e) {
            LOG.error(e.getMessage());
        } catch (JMSException e) {
            LOG.error(e.getMessage());
        }
    }

    public void shutdown() {
        LOG.trace("Stopping listener");
        tcpListener.stop();

        LOG.trace("Undeploying engine");
        engine.undeploy();
        deployed = false;

        LOG.trace("Done");
    }

    public boolean isDeployed() {
        return deployed;
    }

    public String getQueueName() {
        return "queue/" + queueName;
    }

    private Settings createEngineSettings() {
        final Settings settings = new Settings();
        settings.setStringProperty(FFMQCoreSettings.DESTINATION_DEFINITIONS_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.BRIDGE_DEFINITIONS_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.TEMPLATES_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.DEFAULT_DATA_DIR, ".");

        return settings;
    }
}
