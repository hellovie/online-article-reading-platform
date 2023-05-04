package io.github.hellovie.core.util;

import com.github.ksuid.KsuidGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * ksuid 生成器. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2022/12/29 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class KsuidIdentifierGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return KsuidGenerator.generate();
    }
}
