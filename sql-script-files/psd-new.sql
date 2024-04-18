-- Для нового портала
select
	st."number" as "ID на новом портале",
	max(ppp.full_title) as "Название услуги",
	'https://new.gu.lenobl.ru/services/target/'||st."number"::text||'/get' as "Ссылка на услугу"
from
	public.service_target st
join
	public.rgu_service rs
	on rs.service_id = st.service_id
join
	public.pguapi_ps_passport ppp
	on ppp.id = rs.code::int8
where
	st.show_on_pgu is true
	and ppp.status not like 'DELET%'
group by "ID на новом портале", "Ссылка на услугу";