-- Для старого портала
select
    s.id as "ID услуги",
    s.description as "Название услуги",
    'https://gu.lenobl.ru/Pgu/?page-url=private.reg_docs&serviceId='||s.id::text as "Ссылка на услугу",
    s.app_name as "Залупа"
from
    uc.services s
where
        app_name like 'mfc%'
  and s.is_active_version is true
  and s.digital_available is true
  and s.id not in (
    select
        distinct sct.service_id
    from
        uc.services_constructor_targets sct
)
  and s.id in (
    select
        pp.digital_service
    from
        rgu.ps_passport pp
    where
            pp.status not like 'DELET%'
      and digital_service is not null
);