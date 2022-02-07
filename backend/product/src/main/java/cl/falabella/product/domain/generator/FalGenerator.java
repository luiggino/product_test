package cl.falabella.product.domain.generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class FalGenerator implements IdentifierGenerator, Configurable {

    private String prefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

        var query = String.format("select %s from %s",
                session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        Stream<String> ids = session.createQuery(query).stream();

        var max = ids.map(o -> o.replace(prefix + "-", ""))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);

        var value = max + 1L;
        return prefix + "-" + String.format("%1$-" + 7 + "s", value).replace(' ', '0');
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }

}