package br.com.knewin.desafio.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class New {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    private String title;

    private String subtitle;

    private String author;

    private String date;

    @Column(length = 30000)
    private String content;

}
