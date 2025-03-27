# Use uma imagem base Node.js para construir o aplicativo
FROM node:22-alpine as builder

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie os arquivos do projeto para o contêiner
COPY package.json ./
COPY package-lock.json ./

# Instale as dependências do projeto
RUN npm ci

# Instale o Angular CLI globalmente
RUN npm install -g @angular/cli

# Copie o restante do código-fonte
COPY . .

# Build o aplicativo Angular para produção
RUN npm run build

# Use uma imagem base Nginx para servir os arquivos estáticos
FROM nginx:1.23-alpine

# Exponha a porta 80 para acesso externo
EXPOSE 80

# Remova a configuração padrão do Nginx
RUN rm -rf /etc/nginx/conf.d/*

# Copie a configuração do Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copie os arquivos estáticos do build do Angular para o diretório de arquivos do Nginx
COPY --from=builder /app/dist/app/browser /usr/share/nginx/html
