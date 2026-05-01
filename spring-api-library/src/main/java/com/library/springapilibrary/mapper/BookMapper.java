package com.library.springapilibrary.mapper;

import com.library.springapilibrary.dto.BookDTO;
import com.library.springapilibrary.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Book entities and BookDTOs using MapStruct.
 *
 * @Mapper: This is a MapStruct annotation that triggers the code generation for this interface.
 *          - componentModel = "spring": Tells MapStruct to generate an implementation that is a
 *            Spring @Component, making it injectable.
 *
 *          - Jakarta EE Equivalent: @Mapper(componentModel = "cdi"). This would generate an
 *            implementation that is a CDI bean (e.g., @ApplicationScoped), making it injectable
 *            via @Inject in a Jakarta EE environment.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * An instance of the mapper that can be used if dependency injection is not available.
     * Not typically used when componentModel is "spring", but good practice to include.
     */
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    /**
     * Converts a Book entity to a BookDTO.
     * MapStruct will automatically generate the implementation for this method,
     * matching fields by name (e.g., book.getTitle() -> bookDTO.setTitle(...)).
     *
     * @param book The source Book entity.
     * @return The resulting BookDTO.
     */
    BookDTO toDTO(Book book);

    /**
     * Converts a BookDTO to a Book entity.
     *
     * @param dto The source BookDTO.
     * @return The resulting Book entity.
     */
    Book toEntity(BookDTO dto);

    /**
     * Updates an existing Book entity from a BookDTO.
     *
     * @MappingTarget: This annotation tells MapStruct that the 'entity' parameter is the
     * mapping target. Instead of creating a new Book instance, it will update the one
     * that is passed in. This is perfect for update operations where you fetch an entity
     * from the database and then apply new values to it from the request DTO.
     *
     * @param dto The source BookDTO containing the new data.
     * @param entity The target Book entity to be updated.
     */
    void updateEntityFromDTO(BookDTO dto, @MappingTarget Book entity);
}
