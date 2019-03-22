package ch.hslu.appe.fbs.wrapper;

public interface Wrappable<ENTITY, DTO> {

    DTO dtoFromEntity(ENTITY entity);

    ENTITY entityFromDTO(DTO dto);
}
