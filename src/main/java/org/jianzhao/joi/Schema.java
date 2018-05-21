package org.jianzhao.joi;

@FunctionalInterface
public interface Schema<T> {

    Result validate(T target);

    default Schema<T> compose(Schema<T> after) {
        return target -> {
            Result rb = this.validate(target);
            return rb.isInvalid() ? rb : after.validate(target);
        };
    }

    default Schema<T> invert(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message require non-null!");
        }
        return target -> this.validate(target).isInvalid() ? Result.valid() : Result.of(message);
    }

}
