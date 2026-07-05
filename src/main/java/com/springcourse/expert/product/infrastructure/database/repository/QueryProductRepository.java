package com.springcourse.expert.product.infrastructure.database.repository;

import com.springcourse.expert.product.infrastructure.database.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * El nombre Query es solo para distinguirlo del otro ProductRepository para no utilizar varias implementaciones
 *
 * */

/*
 * SE ESPECIFICA QUE EXTIENDE DE:
 *
 *
 * JpaRepository<Entity,PrimaryKey>

 * extends JpaRepository<ProductEntity,Long>
 *
 * */
/*
 * Se anota como un bean repository
 * */
@Repository

/*
 *
 * JpaSpecificationExecutor<ProductEntity>: Permite ejecutar especificaciones siguiendo la logica base de JPA
 * Utilizando una serie de funciones para poder crear QUERYS A MANO
 * */
public interface QueryProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {

    /*
     * SE PUEDEN CREAR QUERYS PERSONALIZADAS ESPECIFICANDO NOMBRES
     * ESPECIFICANDO TAMBIEN QUE ATRIBUTOS DE LA TABLA TOMAR DE REFERENCIA
     *
     * */
    Optional<ProductEntity> findByName(String name);

    /*
     * Containing: SON CONSULTAS APROXIMADAS CON EL PARAMETRO PASADO
     * */
    Optional<ProductEntity> findByNameContaining(String name);

    List<ProductEntity> findAllByPriceBetween(Double priceAfter, Double priceBefore);

    /*SE PUEDEN GENERAR DE CUALQUIER QUERY E IR CONCATENANDO PARAMETROS NECESARIOS PERO
     * TIENDEN A HACERSE MUY LARGAS
     *
     * POR LO TANTO SE RECOMIENDA @Query("query")
     * */
    List<ProductEntity> findAllByNameContainingAndDescriptionContainingOrPriceBetween(String name, String description, Double priceAfter, Double priceBefore);

    /*
     * SE PUEDEN GENERAR QUERYS MAS COMPLEJAS DESDE JPQL
     * PERMITEN PODER DEFINIR QUERYS EN BASE A PARAMETROS Y PODER USAR NOMBRES MAS REDUCIDOS
     *
     *
     * SE PUEDEN UTILIZAR PARAMETROS EN ORDEN
     *
     * @Query("SELECT p FROM ProductEntity p WHERE p.name LIKE %?1% OR p.description LIKE %?2% OR p.price BETWEEN ?3 AND ?4")
     *
     * O UTILIZAR CONCAT O DECLARAR LAS VARIABLES
     *
     * */
    @Query("SELECT p FROM ProductEntity p WHERE p.name LIKE concat( '%',:name,'%') OR p.description LIKE concat( '%',:description,'%') OR p.price BETWEEN :priceAfter AND :priceBefore")
    List<ProductEntity> findProductDetails(String name, String description, Double priceAfter, Double priceBefore);

    /*Verificar existencia*/
    boolean existsByName(String name);

    /*Ejecutar contadores*/
    long countByPrice(Double price);


    /*
     * REALIZAR CONSULTAS PAGINADAS:
     *
     * ESTA CLASE PERMITE PODER RETORNAR INTERVALOS PAGINADOS DE INFORMACION
     *
     * con el atributo specification permite especificar que parametros van a servir para poder filtrar
     */

    Page<ProductEntity> findAll(Specification<ProductEntity> specification, Pageable pageable);

    /*
     *
     *  Al generar la query hace el join para traer su relacion
     *  HACE UNAS SOLA QUERY HACIENDO UN JOIN
     *  @EntityGraph(attributePaths = {"productDetailEntity"})
     *
     * PERMITE ESPECIFICAR QUE SE VA A CARGAR AL MOMENTO DE HACER LA QUERY. ES DECIR
     * SE VA A CARGAR LOS DATOS DE ESAS TABLAS DIRECTO CUANDO SE BUSQUE EL OBJETO
     * */
    @EntityGraph(attributePaths = {"productDetail", "reviews", "categories"})
    Optional<ProductEntity> findById(Long id);
}
