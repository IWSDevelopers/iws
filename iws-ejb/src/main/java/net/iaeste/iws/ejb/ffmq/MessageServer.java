/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-ejb) - net.iaeste.iws.ejb.ffmq.MessageServer
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.ejb.ffmq;

import net.iaeste.iws.api.constants.IWSErrors;
import net.iaeste.iws.api.exceptions.IWSException;
import net.timewalker.ffmq3.FFMQCoreSettings;
import net.timewalker.ffmq3.listeners.ClientListener;
import net.timewalker.ffmq3.listeners.tcp.io.TcpListener;
import net.timewalker.ffmq3.local.FFMQEngine;
import net.timewalker.ffmq3.management.destination.definition.QueueDefinition;
import net.timewalker.ffmq3.utils.Settings;
import org.apache.log4j.Logger;

import javax.jms.JMSException;

/**
 * To start the server, do following:
 * MessageServer ms = new MessageServer();
 * ms.start();
 * while(!ms.isDeployed()) {
 *     //maybe some counter for timeout
 *     Thread.sleep(1000);
 * }
 *
 * @author  Pavel Fiala / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class MessageServer {
    public static final String queueNameForFfmq = "iws-EmailQueue";
    public static final String queueNameForIws = "query/iws-EmailQueue";

    public static final String engineName = "IwsFfmqMessageServer";
    public static final String listenAddr = "0.0.0.0";
    public static final int listenPort = 10002;

    private static final Logger LOG = Logger.getLogger(MessageServer.class);

    private FFMQEngine engine;
    private ClientListener tcpListener;

    private boolean deployed = false;


    public void run() {
        try {
            System.setProperty("FFMQ_BASE", "..");

            final Settings settings = createEngineSettings();
            engine = new FFMQEngine(MessageServer.engineName, settings);

            LOG.trace("Starting listener");
            tcpListener = new TcpListener(engine, MessageServer.listenAddr, MessageServer.listenPort, settings, null);
            tcpListener.start();

            final QueueDefinition queueDef = new QueueDefinition();
            queueDef.setName(MessageServer.queueNameForFfmq);
            queueDef.setUseJournal(false);
            queueDef.setMaxNonPersistentMessages(1000);
            queueDef.check();

            if(engine.getDestinationDefinitionProvider().hasQueueDefinition(MessageServer.queueNameForFfmq)) {
                engine.getDestinationDefinitionProvider().removeQueueDefinition(queueDef);
            }

            engine.createQueue(queueDef);

            LOG.trace("Deploying engine " + engine.getName());
            engine.deploy();

            deployed = true;
            LOG.trace("Running");
        } catch (JMSException e) {
            throw new IWSException(IWSErrors.ERROR, "Setting up FFMQ server failed.", e);
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

    private Settings createEngineSettings() {
        final Settings settings = new Settings();
        settings.setStringProperty(FFMQCoreSettings.DESTINATION_DEFINITIONS_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.BRIDGE_DEFINITIONS_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.TEMPLATES_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.DEFAULT_DATA_DIR, ".");

        return settings;
    }
}
