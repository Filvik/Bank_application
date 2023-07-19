DELETE
     From operations;

DELETE
     From account;

INSERT INTO public.account(
	    id, balance, type_currency)
	    VALUES (1000, 1200, 'usdt');

INSERT INTO public.operations(
        data, id_account, id_type_operation, sum)
    	VALUES (1, 1000, 1, 11),(2, 1000, 1, 12),(3, 1000, 2, 13),(4, 1000, 1, 14),(5, 1000, 2, 15),
    	(6, 1000, 2, 16),(7, 1000, 2, 17),(8, 1000, 1, 18),(9, 1000, 1, 19),(10, 1000, 2, 20);

