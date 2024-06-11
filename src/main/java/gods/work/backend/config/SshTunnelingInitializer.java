//package gods.work.backend.config;
//
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import jakarta.annotation.PreDestroy;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.annotation.Validated;
//
//import java.util.Properties;
//
//@Slf4j
//@Component
//@ConfigurationProperties(prefix = "ssh")
//@Validated @Setter
//public class SshTunnelingInitializer {
//
//    private String remoteJumpHost;
//    private int sshPort;
//    private String user;
//    private String privateKeyPath;
//    private int databasePort;
//
//    private Session session;
//
//    @PreDestroy
//    public void destroy() {
//        if (session.isConnected())
//            session.disconnect();
//    }
//
//    public Integer buildSshConnection() {
//        Integer forwardPort = null;
//
//        try {
//            log.info("Connecting to SSH with {}@{}:{} using privateKey at {}", user, remoteJumpHost, sshPort, privateKeyPath);
//            JSch jsch = new JSch();
//
//            // connection 1. application server to jump server
//            jsch.addIdentity(privateKeyPath); // pem 키 추가
//            session = jsch.getSession(user, remoteJumpHost, sshPort); // 세션 설정
//            Properties config = new java.util.Properties();
//            config.put("StrictHostKeyChecking", "no");
//            config.put("PreferredAuthentications", "publickey");
//            config.put("PubkeyAcceptedAlgorithms", "rsa-sha2-256,rsa-sha2-512");
//            session.setConfig(config);
//
//            log.info("Starting SSH session connection...");
//            session.connect(); // 연결
//            log.info("SSH session connected");
//
//            // connection 2. jump server to remote server
//            forwardPort = session.setPortForwardingL(0, "localhost", databasePort);
//            log.info("Port forwarding created on local port {} to remote port {}", forwardPort, databasePort);
//
//        } catch (JSchException e) {
//            log.error(e.getMessage());
//            this.destroy();
//            throw new RuntimeException(e);
//        }
//
//        return forwardPort;
//    }
//}
