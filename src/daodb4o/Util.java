package daodb4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;
import model.Viagem;
import model.Veiculo;
import model.Motorista;

public class Util {
    private static ObjectContainer manager = null;

    public static ObjectContainer conectarBanco() {
        if (manager != null)
            return manager; // já tem uma conexão

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().messageLevel(0);

        config.common().objectClass(Veiculo.class).cascadeOnDelete(false);
        config.common().objectClass(Veiculo.class).cascadeOnUpdate(true);
        config.common().objectClass(Veiculo.class).cascadeOnActivate(true);
        config.common().objectClass(Motorista.class).cascadeOnDelete(false);
        config.common().objectClass(Motorista.class).cascadeOnUpdate(true);
        config.common().objectClass(Motorista.class).cascadeOnActivate(true);
        config.common().objectClass(Viagem.class).cascadeOnDelete(false);
        config.common().objectClass(Viagem.class).cascadeOnUpdate(true);
        config.common().objectClass(Viagem.class).cascadeOnActivate(true);

        manager = Db4oEmbedded.openFile(config, "banco.db4o");
        return manager;
    }

    public static ObjectContainer conectarBancoRemoto() {
        ClientConfiguration config = Db4oClientServer.newClientConfiguration();
        config.common().messageLevel(0);

        config.common().objectClass(Veiculo.class).cascadeOnDelete(false);
        config.common().objectClass(Veiculo.class).cascadeOnUpdate(true);
        config.common().objectClass(Veiculo.class).cascadeOnActivate(true);
        config.common().objectClass(Motorista.class).cascadeOnDelete(false);
        config.common().objectClass(Motorista.class).cascadeOnUpdate(true);
        config.common().objectClass(Motorista.class).cascadeOnActivate(true);
        config.common().objectClass(Viagem.class).cascadeOnDelete(false);
        config.common().objectClass(Viagem.class).cascadeOnUpdate(true);
        config.common().objectClass(Viagem.class).cascadeOnActivate(true);

        String ipservidor = "";
        ipservidor = "54.163.92.174"; // AWS
        manager = Db4oClientServer.openClient(config, ipservidor, 34000, "usuario1", "senha1");
        return manager;
    }

    public static void desconectar() {
        if (manager != null) {
            manager.close();
            manager = null;
        }
    }
}