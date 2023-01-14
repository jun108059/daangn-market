insert
into member
(email, password, name, nickname, phone, area, role, created_at, updated_at)
values ('youngjun108059@gmail.com', '$2a$10$4LbNcwtER6WCKM9lBFffwu62T4ETFaELybBQ3Nu8YG/ar.J35vNb.', '박영준', '만두',
        '010-1234-1234', '판교', 'ROLE_USER', '2023-01-14 15:22:54.173854', '2023-01-14 15:22:54.173854');

insert
into product
(title, content, category, area, member_id, price, status, created_at, updated_at)
values ('한남더힐 100평 펜트하우스 매매', '300억에 급매합니다 ^^', 'OTHER_USED_GOODS', '판교', 1, 30000000000, 'TRADING',
        '2023-01-14 15:22:54.173854', '2023-01-14 15:22:54.173854');

insert
into image
    (image_id, file_name, product_id, created_at, updated_at)
    value
    (1, 'https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201708/05/b313969a-a073-4665-a721-8af77dc1df39.jpg',
     1, '2023-01-14 15:22:54.173854', '2023-01-14 15:22:54.173854')