# `AXUR - Teste técnico: Software Development Intern`

## Conteúdo

 - [`Adicionando .editorconfig e .gitignore`](#editorconfig-gitignore)
 - [`Como iniciar um projeto (vazio) com gradle`](#gradle-init)
 - [`Adicionando Linters e Formatadores de código no Java`](#lint-formatter)
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

<div id="gradle-init"></div>

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

<div id="lint-formatter"></div>

## `Adicionando Linters e Formatadores de código no Java`

Aqui, nós vamos configurar **linters** e **formatadores no Java**, focando em **boas práticas, estilo e erros comuns**, usando ferramentas padrão da comunidade Java e integração com o **VSCode**.

### `Instalando extensões no VSCode`

Vamos começar instalando as seguintes extensões no VSCode:

 - **Extension Pack for Java** *(obrigatória)*
   - ID: `vscjava.vscode-java-pack`
 - **Checkstyle for Java**
   - ID: `shengchen.vscode-checkstyle`
 - **Google Java Format** *(formatter)*

### `Configurando o Linter (Checkstyle)`

> Aqui nós vamos criar o arquivo `checkstyle.xml` que é o responsável por definir todas as regras de estilo, organização e boas práticas que o *Checkstyle* vai aplicar ao código Java do projeto.

**Ele funciona de forma muito parecida com o pyproject.toml no Python quando usamos o Ruff:**  
é aqui que você decide o que é permitido, o que é proibido e como o código deve se parecer.

Sempre que o Checkstyle roda (no VSCode, no terminal ou no CI), ele:

 - Analisa os arquivos `.java`
 - Aplica essas regras
 - Reporta erros e avisos quando algo foge do padrão definido

O nosso `checkstyle.xml` vai ficar da seguinte maneira:

[checkstyle.xml](checkstyle.xml)
```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
    "https://checkstyle.org/dtds/configuration_1_3.dtd">

<module name="Checker">

    <!-- Define arquivos ignorados -->
    <property name="excludes" value="**/generated/**"/>

    <module name="TreeWalker">

        <!-- Tamanho máximo da linha -->
        <module name="LineLength">
            <property name="max" value="100"/>
        </module>

        <!-- Imports organizados -->
        <module name="ImportOrder">
            <property name="ordered" value="true"/>
            <property name="separated" value="true"/>
        </module>

        <!-- Boas práticas -->
        <module name="UnusedImports"/>
        <module name="RedundantImport"/>
        <module name="AvoidStarImport"/>

        <!-- Convenções de nomes -->
        <module name="MethodName"/>
        <module name="MemberName"/>
        <module name="ClassName"/>

    </module>
</module>
```

Agora, vamos para algumas explicações:

**Cabeçalho e DTD**
```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
    "https://checkstyle.org/dtds/configuration_1_3.dtd">
```

> **O que esse bloco faz?**

 - Declara que o arquivo é um XML válido
 - Define o DTD oficial do Checkstyle
 - Permite que o Checkstyle:
   - Valide a estrutura do arquivo
   - Saiba quais módulos e propriedades são aceitos
 - *📌 Sem esse bloco, o Checkstyle não consegue interpretar corretamente o arquivo.*

**Módulo raiz — `Checker`**
```xml
<module name="Checker">

    ...

</module>
```

> **O que é o `Checker`?**

 - É o módulo raiz do Checkstyle
 - Todo arquivo de configuração sempre começa por ele
 - Ele coordena a execução de todas as regras
 - Pense nele como:
   - *“O motor principal que roda todas as verificações”.*

**Exclusão de arquivos:**
```xml
<!-- Define arquivos ignorados -->
<property name="excludes" value="**/settings/**,**/generated/**"/>
```

> **O que esse bloco faz?**

 - Diz ao Checkstyle para ignorar arquivos ou pastas
 - O padrão usa glob patterns (`**`)
 - Nesse caso:
   - Qualquer arquivo dentro de uma pasta `settings` ou `generated`
   - Não será analisado pelo Checkstyle

**Analisador de código — `TreeWalker`**
```xml
<module name="TreeWalker">

    ...

</module>
```

> **O que é o `TreeWalker`?**

 - É o módulo responsável por percorrer a *árvore sintática (AST)* do código Java
 - A maioria das regras de estilo e boas práticas vivem aqui dentro
 - **📌 Regra prática:**
   - Quase todas as regras de código Java ficam dentro do `TreeWalker`

**Regra de tamanho máximo de linha**
```xml
<module name="LineLength">
    <property name="max" value="100"/>
</module>
```

> **O que esse bloco faz?**

 - Define o tamanho máximo permitido para uma linha
 - Se uma linha *ultrapassar 100 caracteres*:
   - O Checkstyle gera um erro ou warning

**Organização de imports:**
```xml
<module name="ImportOrder">
    <property name="ordered" value="true"/>
    <property name="separated" value="true"/>
</module>
```

> **O que esse bloco faz?**

 - Garante que:
   - Os imports estejam em ordem alfabética
   - Grupos de imports sejam separados por linha em branco

**Boas práticas de imports:**
```xml
<module name="UnusedImports"/>
<module name="RedundantImport"/>
<module name="AvoidStarImport"/>
```

> **O que cada regra faz?**

 - `UnusedImports`
   - Detecta imports que não estão sendo usados
 - `RedundantImport`
   - Detecta imports desnecessários
 - `AvoidStarImport`
   - Proíbe imports do tipo:
     - `import java.util.*;`

**Convenções de nomenclatura:**
```xml
<module name="MethodName"/>
<module name="MemberName"/>
<module name="ClassName"/>
```

> **O que esse bloco faz?**

Garante que os nomes sigam o padrão Java:

 - `ClassName` → `PascalCase`
 - `methodName` → `camelCase`
 - `memberName` → `camelCase`

> **NOTE:**  
> Agora, se você quebrar alguma destas regras é só abrir o VSCode em um arquivo específico que ele vai alertar (warning) os erros.

### `Configurando o Checkstyle no VSCode`

> Agora, nós vamos configurar o arquivo `.vscode/settings.json` que vai definir configurações específicas do projeto no *VSCode*.

Tudo que está aqui:

 - Vale apenas para esse workspace
 - Garante que todo mundo do time use o mesmo comportamento
 - Evita *“funciona na minha máquina”*
 - Nesse caso, ele está sendo usado para:
   - Ativar o Checkstyle
   - Apontar o arquivo de regras
   - Configurar o formatador automático (Google Java Format)

[settings.json](.vscode/settings.json)
```json
{
    "checkstyle.configuration": "${workspaceFolder}/checkstyle.xml",
    "checkstyle.enable": true,
    "checkstyle.run": "onType",

    "java.compile.nullAnalysis.mode": "automatic",

    "editor.defaultFormatter": "wx-chevalier.google-java-format",

    "[java]": {
        "editor.formatOnSave": true
    }
}
```

 - `"checkstyle.configuration": "${workspaceFolder}/checkstyle.xml"`
   - Informa ao VSCode onde está o arquivo de regras do Checkstyle
   - `${workspaceFolder}` aponta para a raiz do projeto
   - O VSCode carrega esse arquivo e aplica exatamente as regras definidas nele
   - *📌 Sem essa configuração, o Checkstyle não sabe quais regras usar.*
 - `"checkstyle.enable": true`
   - Ativa o Checkstyle neste projeto
   - Se estiver *false*, o lint fica totalmente des*abilitado
   - *📌 É o “liga/desliga” do Checkstyle.*
 - `"checkstyle.run": "onType"`
   - Define quando o Checkstyle deve rodar:
     - `onType` → enquanto você digita
     - `onSave` → apenas ao salvar
     - `manual` → somente quando você mandar rodar
   - *📌 onType dá feedback imediato.*
 - `"java.compile.nullAnalysis.mode": "automatic"`
   - Ativa a análise estática de valores nulos do compilador Java
   - O VSCode tenta detectar:
     - Possíveis *NullPointerException*
     - Atribuições inseguras
     - Retornos que podem ser null
 - `"editor.defaultFormatter": "wx-chevalier.google-java-format"`
   - Define qual ferramenta será usada para formatar o código
   - Força o uso do Google Java Format
   - Evita conflitos com outros formatadores instalados
 - `"[java]": {"editor.formatOnSave": true}`
   - Aplica a configuração somente para arquivos `.java`
   - Sempre que você salvar um arquivo Java:
     - O VSCode formata automaticamente o código
   - *📌 Garante código sempre padronizado, sem esforço manual.*

---

**Rodrigo** **L**eite da **S**ilva - **rodirgols89**
