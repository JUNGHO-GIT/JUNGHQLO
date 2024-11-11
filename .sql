UPDATE
  product
SET
  product_imgsUrl = REPLACE(product_imgsUrl, 'https://storage.googleapis.com/jungho-bucket/JUNGHQLO/DB/', '')
WHERE
  product_imgsUrl LIKE '%https://storage.googleapis.com/jungho-bucket/JUNGHQLO/DB/%';