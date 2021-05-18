package org.sberbank.simonov.bank.util;

import org.sberbank.simonov.bank.exception.ImpossibleToCreateEntityException;
import org.sberbank.simonov.bank.exception.NotFoundException;
import org.sberbank.simonov.bank.model.abstraction.BaseEntity;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            throw new NotFoundException(arg);
        }
    }

    public static <T extends BaseEntity> void checkCreate(boolean found, T entity) {
        if (!found) {
            throw new ImpossibleToCreateEntityException(String.format("Entity %d not created", entity.getId()));
        }
    }

    public static void checkNew(BaseEntity entity) {
        if (entity.hasId()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(BaseEntity entity, int id) {
        // http://stackoverflow.com/a/32728226/548473
        if (!entity.hasId()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }
}
