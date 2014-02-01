/*
 * =============================================================================
 * Copyright 1998-2014, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @since   IWS 1.0
 */
public class MessageServer extends Thread {

    private static final Logger log = LoggerFactory.getLogger(MessageServer.class);

    public static final String queueNameForFfmq = "IwsEmailQueue";
    public static final String queueNameForIws = "queue/IwsEmailQueue";
    public static final String engineName = "IwsFFMQMessageServer";
    public static final String listenAddr = "0.0.0.0";
    public static final int listenPort = 10002;
    private FFMQEngine engine;
    private ClientListener tcpListener;
    private boolean deployed = false;

    @Override
    public void run() {
        try {
            System.setProperty("FFMQ_BASE", "..");

            final Settings settings = createEngineSettings();
            engine = new FFMQEngine(engineName, settings);

            log.trace("Starting listener");
            tcpListener = new TcpListener(engine, listenAddr, listenPort, settings, null);
            tcpListener.start();

            final QueueDefinition queueDef = new QueueDefinition();
            queueDef.setName(queueNameForFfmq);
            queueDef.setUseJournal(false);
            queueDef.setMaxNonPersistentMessages(1000);
            queueDef.check();

            if(!engine.getDestinationDefinitionProvider().hasQueueDefinition(queueNameForFfmq)) {
                engine.createQueue(queueDef);
            } else {
                engine.getDestinationDefinitionProvider().removeQueueDefinition(queueDef);
                engine.createQueue(queueDef);
            }

            log.trace("Deploying engine " + engine.getName());
            engine.deploy();

            deployed = true;
            log.trace("Running");
        } catch (JMSException e) {
            throw new IWSException(IWSErrors.ERROR, "Setting up FFMQ server failed.", e);
        }
    }

    public void shutdown() {
        log.trace("Stopping listener");
        tcpListener.stop();

        log.trace("Undeploying engine");
        engine.undeploy();
        deployed = false;

        log.trace("Done");
    }

    public boolean isDeployed() {
        return deployed;
    }

    private static Settings createEngineSettings() {
        final Settings settings = new Settings();
        settings.setStringProperty(FFMQCoreSettings.DESTINATION_DEFINITIONS_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.BRIDGE_DEFINITIONS_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.TEMPLATES_DIR, ".");
        settings.setStringProperty(FFMQCoreSettings.DEFAULT_DATA_DIR, ".");

        return settings;
    }
}
