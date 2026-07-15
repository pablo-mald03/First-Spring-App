package com.springcourse.expert.common.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/*
 * Clase utilizada para poder registrar en la base de datos atributos en tablas que permiten saber cuando
 * se creo o se modifico
 *
 * SIRVE PARA AUDITAR LAS TABLAS Y ENTIDADES
 *
 * */
@Getter
/*
 * LA ANOTACION @MappedSuperclass LE INDICA A HIBERNATE QUE LA CLASE NO REPRESENTA UNA TABLA PROPIA
 * SINO QUE SUS CAMPOS DEBEN COPIARSE A LAS CLASES HIJAS
 *
 *  */
@MappedSuperclass
/*
 * @EntityListeners(AuditingEntityListener.class): ES QUIEN ESCUCHA EVENTOS DE HIBERNATE
 *
 *
 * */
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    /*
     *  @CreatedDate: INDICA QUE ESTE CAMPO SE LLENARA CUANDO EL REGISTRO SE CREE
     *  PERMITIENDO LLEVAR UN CONTROL DE CREACION
     *
     * */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    /*
     * @LastModifiedDate: CAMPO QUE SIEMPRE SE ACTUALIZARA AL MOMENTO DE QUE
     * LA ENTIDAD EN LA TABLA SE MODIFIQUE.
     *
     *  */
    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
}
