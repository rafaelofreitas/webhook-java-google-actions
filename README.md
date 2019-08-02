# Actions on Google

Webhook-Java-Google-Actions pode ajudar você a começar rapidamente com a biblioteca cliente Java para Ações no Google.

### Instruções de Configuração

#### Action Configuration
1. No [Actions on Google Console](https://console.actions.google.com/), Novo projeto (este será o seu *ID do projeto* )> **Criar projeto**.
1. Role para baixo até a seção **Mais opções** e clique no cartão *Conversacional* .
1. No menu superior, em **Desenvolvimento > Ações** (navegação à esquerda)> **Adicionar sua primeira ação > BUILD** (isso levará você ao console do Dialogflow)> Selecionar idioma e fuso horário> **CRIAR** .
1. No Dialogflow, vá para **Configurações > Exportar e Importar > Restaurar do zip** .
    + Siga as instruções para restaurar a partir do `agent.zip` arquivo neste repo.

#### Implantação do Google App Engine e configuração de webhook
Quando um novo projeto é criado usando o Console de ações, ele também cria um projeto do Google Cloud em segundo plano.
1. Excluir class ActionsAWSHandler.java
1. Remova a seguinte linha do build.gradle:
     + `apply from: 'build-aws.gradle'`
1. Faça o download e instale o [SDK do Google Cloud](https://cloud.google.com/sdk/docs/)
1. Configure a CLI gcloud e defina seu projeto do Google Cloud como o nome de suas ações no código do projeto do Google, que você pode encontrar [no console "Ações no Google",](https://console.actions.google.com/) em Configurações ⚙
    + `gcloud init`
    + `gcloud auth application-default login`
    + `gcloud components install app-engine-java`
    + `gcloud components update`
1. Implante no  [App Engine usando o Gradle](https://cloud.google.com/appengine/docs/flexible/java/using-gradle):
    + `gradle appengineDeploy` OU
    +  De dentro do IntelliJ, abra a bandeja Gradle e execute a tarefa appEngineDeploy.
1. De volta ao [console do Dialogflow](https://console.dialogflow.com), no menu de navegação à esquerda, em **Fulfillment** > **Enable Webhook**, defina o valor de **URL** como `https://<SEU_PROJETO_ID>.appspot.com` > **Save**.

#### Construir para a AWS
1. Excluir ActionsServlet
1. Remova a seguinte linha do build.gradle:
    + `apply from: 'build-gcp.gradle'`
1. Crie o arquivo zip compatível com o AWS Lambda usando a tarefa gradle buildAWSZip: `gradle buildAWSZip`
1. Implante o arquivo zip encontrado `build/distributions/myactions.zip` como uma função do AWS Lambda seguindo as instruções em https://aws.amazon.com/lambda/

#### Testando este exemplo
+ No [console do Dialogflow](https://console.dialogflow.com), no menu de navegação à esquerda> **Integrações > Configurações de integração**, no Assistente do Google, ative as **alterações na visualização automática > Teste** para abrir o simulador Ações no Google. **OU**
+ Digite `Falar com meu app de teste` o simulador ou diga, ou diga `OK Google, Falar com meu app de teste` ao Google Assistente em um dispositivo móvel associado à conta da sua ação.

