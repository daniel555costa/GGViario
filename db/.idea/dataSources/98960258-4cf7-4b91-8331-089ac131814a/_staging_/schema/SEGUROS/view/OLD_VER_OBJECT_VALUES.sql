create or REPLACE view OLD_VER_OBJECT_VALUES as
SELECT
       OB.OBJVALL_ID AS "ID",
       OB.OBJVALL_OBJ_ID AS "ID OBJECTO",
       OB.OBJVALL_CTT_ID AS "ID CONTRATO",
        CASE
            WHEN OB.OBJVALL_CTT_ID IS NOT NULL THEN OB.OBJVALL_CTT_ID
            WHEN OB.OBJVALL_OBJ_ID IS NOT NULL THEN ASSCTT.CTT_ID
            ELSE NULL
        END AS "ID CONTRATO INCLUDE",
          
       OB.OBJVALL_OBJVALL_ID AS "SUPER",
       CLA.CLASSATB_NAME AS "ATRIBUTO",
       (CASE CLA.CLASSATB_TYPE
           WHEN 2 THEN CAST((TRIM(REPLACE(OB.OBJVALL_VALUE, ',', '.'))) AS VARCHAR2(130))
           WHEN 3 THEN CAST(TRIM(REPLACE(OB.OBJVALL_VALUE, ',', '.')) AS VARCHAR2(130))
           WHEN 4 THEN TO_CHAR(TO_DATE(OB.OBJVALL_VALUE , 'yy.mm.dd'), 'DD-MM-YYYY')
           -- Tipo Money 6
           WHEN 6 THEN CAST(TRIM(REPLACE(OB.OBJVALL_VALUE, ',', '.')) AS VARCHAR2(130))
            -- WHEN 6 THEN PACK_LIB.MONEY(TO_NUMBER(REPLACE(OB.OBJVALL_VALUE, ',', '.')))
           ELSE OB.OBJVALL_VALUE 
        END) AS "VALUE",
       CASE
          WHEN CLA.CLASSATB_REFTABLE IS NOT NULL AND 
             CLA.CLASSATB_REFID IS NOT NULL AND
             CLA.CLASSATB_REFVALUES IS NOT NULL THEN 
             PACK_LIB.getVallView(CLA.CLASSATB_REFTABLE, CLA.CLASSATB_REFID, CLA.CLASSATB_REFVALUES, OB.OBJVALL_VALUE , '=', 1)
          ELSE OB.OBJVALL_VALUE 
       END AS "TREAT VALUE",
       CASE WHEN CLA.CLASSATB_NULAVEL = 1 THEN 'YES' ELSE 'NO' END AS "ACCEPT NULL",
       CLA.CLASSATB_REFTABLE AS "TABLE ORIGIN",
       CLA.CLASSATB_REFID AS "TABLE ID",
       CLA.CLASSATB_REFVALUES AS "TABLE VALUE",
       CLA.CLASSATB_CLASS_ID AS "CLASSE",
       CLASSE.CLASS_DESC AS "NOME CLASSE",
       CLA.CLASSATB_TYPE,
       OB.OBJVALL_DTREG AS "REGISTRO"
    FROM T_OBJCLASS  CLASSE
       INNER JOIN T_OBJCLASSATTRIBUTE CLA ON CLASSE.CLASS_ID = CLA.CLASSATB_CLASS_ID
       INNER JOIN  T_OBJECTVALUE OB ON CLA.CLASSATB_ID = OB.OBJVALL_CLASSATB_ID
       LEFT JOIN T_OBJECTO OBJ ON OB.OBJVALL_OBJ_ID = OBJ.OBJ_ID
       LEFT JOIN T_ASSEGURA ASS ON (OBJ.OBJ_ID = ASS.ASE_OBJ_ID AND ASS.ASE_STATE = 1)
       LEFT JOIN T_CONTRATO ASSCTT ON ASS.ASE_CTT_ID = ASSCTT.CTT_ID
    WHERE CLA.CLASSATB_STATE = 1
       AND OB.OBJVALL_STATE = 1
       -- AND CLASSE.CLASS_DESC  IN( 'VEICULO')
    ORDER BY "ID OBJECTO" DESC, CLASSE.CLASS_ID
