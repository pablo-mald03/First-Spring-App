CREATE INDEX idx_reviews_product_id
    ON reviews (product_id);

CREATE INDEX idx_products_product_detail_id
    ON products (product_detail_id);

CREATE INDEX idx_products_categories_product_id
    ON products_categories (product_id);

CREATE INDEX idx_products_categories_category_id
    ON products_categories (category_id);

CREATE INDEX idx_products_name
    ON products (name);

CREATE INDEX idx_categories_name
    ON categories (name);