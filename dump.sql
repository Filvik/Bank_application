PGDMP                         {           Bank_application    15.1    15.1     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    25640    Bank_application    DATABASE     �   CREATE DATABASE "Bank_application" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
 "   DROP DATABASE "Bank_application";
                postgres    false            �            1259    25934    account    TABLE     }   CREATE TABLE public.account (
    balance numeric(38,2),
    id bigint NOT NULL,
    type_currency character varying(255)
);
    DROP TABLE public.account;
       public         heap    postgres    false            �            1259    25937    account_id_seq    SEQUENCE     w   CREATE SEQUENCE public.account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.account_id_seq;
       public          postgres    false    215                        0    0    account_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.account_id_seq OWNED BY public.account.id;
          public          postgres    false    216            �            1259    25740    account_seq    SEQUENCE     u   CREATE SEQUENCE public.account_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.account_seq;
       public          postgres    false            f           2604    25938 
   account id    DEFAULT     h   ALTER TABLE ONLY public.account ALTER COLUMN id SET DEFAULT nextval('public.account_id_seq'::regclass);
 9   ALTER TABLE public.account ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215            �          0    25934    account 
   TABLE DATA           =   COPY public.account (balance, id, type_currency) FROM stdin;
    public          postgres    false    215   c                  0    0    account_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.account_id_seq', 1, false);
          public          postgres    false    216                       0    0    account_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.account_seq', 1, false);
          public          postgres    false    214            h           2606    25940    account account_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.account DROP CONSTRAINT account_pkey;
       public            postgres    false    215            �      x������ � �     