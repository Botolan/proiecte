library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;
--use IEEE.STD_LOGIC_ARITH.all;
use ieee.numeric_std.all;



entity NUMARATOR_ETAJ is
	port(SENS,ENABLE_NUMARATOR_ETAJ,CLOCK:in STD_LOGIC;
	ETAJ_CURENT:out STD_LOGIC_VECTOR(3 downto 0));
end NUMARATOR_ETAJ;



architecture F_NUMARATOR_ETAJ of NUMARATOR_ETAJ is


begin							  
	NUMARARE_ETAJ:process(CLOCK,ENABLE_NUMARATOR_ETAJ)
	variable NUMARATOR_ETAJ:STD_LOGIC_VECTOR(3 downto 0) := "0000";
	begin														  
		if ENABLE_NUMARATOR_ETAJ = '1' then
			if CLOCK = '1' and CLOCK'EVENT then
				if SENS = '1' then
					NUMARATOR_ETAJ := NUMARATOR_ETAJ + '1';
				else
					NUMARATOR_ETAJ := NUMARATOR_ETAJ - '1';
				end if;
			end if;
		end if;
		ETAJ_CURENT <= NUMARATOR_ETAJ;
	end process;
	
	
	
end architecture F_NUMARATOR_ETAJ;
	