package com.edsoft;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class KieSessionFactory implements Serializable {

    static StatelessKieSession statelessKieSession;

    public static StatelessKieSession getKieSession() throws FileNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("kieSession Phase");
        if (statelessKieSession == null)
            statelessKieSession = getNewKieSession();
        return statelessKieSession;
    }

    public static StatelessKieSession getNewKieSession() throws FileNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println("creating a new kie session123");
        /*KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        return kContainer.newKieSession("ksession-rules");*/

        /*FactType factType = KnowledgeBaseFactory.newKnowledgeBase().getFactType("com.edsoft", "ClickStreamCep");

        Object a = factType.newInstance();*/


        KieServices kieServices = KieServices.Factory.get();

        /*KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption( EventProcessingOption.STREAM );*/

        //  FileInputStream resource = new FileInputStream(drlFileName);

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        KieRepository kieRepository = kieServices.getRepository();
        Resource resource = ResourceFactory.newClassPathResource("clikrules.drl");
        //kieResources.newFileSystemResource(drlFileName);
        kieFileSystem.write(resource);
        // kieFileSystem.write("/src/main/resources/clickrules.drl", kieServices.getResources().newInputStreamResource(resource));

        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);

        kb.buildAll();

        if (kb.getResults().hasMessages(Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n"
                    + kb.getResults().toString());
        }

        KieContainer kContainer = kieServices.newKieContainer(kieRepository
                .getDefaultReleaseId());
        return kContainer.newStatelessKieSession();

    }

    public static StatelessKieSession ex() throws FileNotFoundException {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kFileSystem = ks.newKieFileSystem();
        FileInputStream fis = new FileInputStream("/home/edsoft/workspace/DroolsExample/src/main/resources/rules/Sample.drl");

        kFileSystem.write("src/main/resources/somename.drl",
                ks.getResources().newInputStreamResource(fis)); //XXX

        KieBuilder kbuilder = ks.newKieBuilder(kFileSystem);
        kbuilder.buildAll();
        if (kbuilder.getResults().hasMessages(Level.ERROR)) {
            throw new RuntimeException("Build time Errors: " + kbuilder.getResults().toString());
        }
        KieContainer kContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());

        KieBaseConfiguration config = ks.newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);
        //kContainer.newStatelessKieSession(config);
        KieBase kieBase = kContainer.newKieBase(config);
        return kieBase.newStatelessKieSession();
    }
}