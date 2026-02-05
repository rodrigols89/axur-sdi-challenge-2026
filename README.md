# `AXUR - Teste técnico: Software Development Intern`

## Conteúdo

 - [`Adicionando .editorconfig e .gitignore`](#editorconfig-gitignore)
<!---
[WHITESPACE RULES]
- 50
--->


















































---

<div id="editorconfig-gitignore"></div>

## `Adicionando .editorconfig e .gitignore`

De início vamos adicionar os arquivos `.editorconfig` e `.gitignore` na raiz do projeto:

[.editorconfig](.editorconfig)
```conf
# top-most EditorConfig file
root = true

# Unix-style newlines with a newline ending every file
[*]
end_of_line = lf
insert_final_newline = true
charset = utf-8

# 4 space indentation
[*.{py,html, js}]
indent_style = space
indent_size = 4

# 2 space indentation
[*.{json,y{a,}ml,cwl}]
indent_style = space
indent_size = 2
```

[.gitignore](.gitignore)
```conf
É muito grande não vou exibir...
```


















































---

## `Como iniciar um projeto (vazio) com gradle`

Para iniciar a estrtura básica do nosso projeto Java vamos utilizar a ferramenta `gradle`:

```bash
gradle init
```

Agora para saber se está tudo ok vamos utilizar o comando:

```bash
gradle run
```

**OUTPUT:**  
```bash
Calculating task graph as no cached configuration is available for tasks: run

> Task :app:run
Hello World!

BUILD SUCCESSFUL in 12s
2 actionable tasks: 2 executed
Configuration cache entry stored.
```

---

**Rodrigo** **L**eite da **S**ilva - **rodirgols89**
