/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.bootstrap;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Bernhard
 */
public class SSLContextFactory {
    private static final String PROTOCOL ="TLS";
    private static final SSLContext SERVER_CONTEXT;
    
    static {
        Security.addProvider(new BouncyCastleProvider());
        SSLContext serverContext =null;
        
        TrustManagerFactory tmf;
        KeyManagerFactory kmf;
        try {
            KeyStore ks =KeyStore.getInstance("BKS");
            ks.load(KeyStoreContainer.asInputStream(),
                    KeyStoreContainer.getKeyStorePassword());
            
            kmf =KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, KeyStoreContainer.getCertificatePassword());
        } catch (NoSuchAlgorithmException ex) {
            throw new Error("Failed to create ManagerFactory. Algorithm not found", ex);
        } catch (KeyStoreException ex) {
            throw new Error("Failed to create KeyStore.", ex);
        } catch (IOException ex) {
            throw new Error("Failed to load KeyStore", ex);
        } catch (CertificateException ex) {
            throw new Error("Failed to access store.", ex);
        } catch (UnrecoverableKeyException ex) {
            throw new Error("Failed to access certificates", ex);
        }

        try {
            serverContext =SSLContext.getInstance(PROTOCOL);
            serverContext.init(kmf.getKeyManagers(), null, null);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new Error("Failed to initialise the server-side SSLContext", e);
        }
        SERVER_CONTEXT =serverContext;
    }
    
    public static SSLContext getServerContext() {
        return SERVER_CONTEXT;
    }
}
