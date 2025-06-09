# Hotel

Site para reservar quartos de hotel no Brasil

<p align="center">
  <img src="assets/to_git_1.png" width="45%" />
  <img src="assets/to_git_2.png" width="45%" />
</p>
<p align="center">
  <img src="assets/to_git_3.png" width="45%" />
  <img src="assets/to_git_4.png" width="45%" />
</p>
<p align="center">
  <img src="assets/to_git_5.png" width="45%" />
  <img src="assets/to_git_6.png" width="45%" />
</p>

## Rodando localmente

Clone o projeto

```bash
  git clone https://link-para-o-projeto
```

Entre no diretório do projeto

```bash
  cd hotel
```

Rode o comando a seguir dentro da pasta "postgres+pgadmin"

```bash
  docker compose up -d
```

Volte e abra a pasta "Backend" e inicie o servidor

```bash
  env java @/tmp/cp_6416nm7j3lv93z1j2fa7352l1.argfile com.lrittes.Hotel.HotelApplication
```

Volte e abra a pasta "frontend" e Instale as dependências

```bash
  npm install
```

Inicie a aplicação web

```bash
  npm run start
```

## Autores

- [@lrittes](https://www.github.com/lrittes)
