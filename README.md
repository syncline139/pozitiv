### Структура таблиц

#### 1. Таблица Users

Хранит информацию о пользователях.

- **id:** уникальный идентификатор.
- **name:** имя (может быть null, если пользователь не указал).
- **login / email:** уникальный логин или почта (обязательное поле).
- **city:** город (необязательно).
- **last_connect:** дата и время последнего входа.
- **password:** зашифрованный пароль.
- **refresh_token:** если решите хранить JWT refresh token (access token обычно хранится на клиенте).

#### 2. Таблица Products

Хранит информацию о товарах в каталоге:

- **id:** уникальный идентификатор товара.
- **name:** название товара.
- **description:** подробное описание.
- **quantity:** количество товара на складе.

#### 3. Таблица Orders (или Purchase_Requests)

Хранит заявки/заказы пользователей на покупку товара. Если заявка подразумевает выбор одного товара, достаточно одного внешнего ключа. Если требуется поддержка заявок с несколькими товарами, можно создать отдельную таблицу order_items.

Поля:

- **id:** уникальный идентификатор заявки.
- **user_id:** внешний ключ, ссылается на пользователя, оставившего заявку.
- **product_id:** внешний ключ, ссылается на товар.
- **order_date:** дата создания заявки.
- **status:** статус заявки (например, «new», «processing», «completed»).

```SQL
CREATE TABLE users(
   id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   name VARCHAR(255) NULL,
   login VARCHAR(255) NOT NULL UNIQUE,
   email VARCHAR(255) NOT NULL UNIQUE,
   city VARCHAR(255) NULL,
   last_connect TIMESTAMP,
   password VARCHAR(255) NOT NULL,
   refresh_token VARCHAR(255)
);

CREATE TABLE products(
   id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   description TEXT,
   quantity BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE orders (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'new',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```