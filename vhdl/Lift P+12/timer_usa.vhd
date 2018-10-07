library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;
--use IEEE.STD_LOGIC_ARITH.all;
use ieee.numeric_std.all;



entity TIMER_USA is
	port(REZULTAT_VALIDARE:in STD_LOGIC_VECTOR(1 downto 0);
	ETAJ_MINIM_MAXIM:in STD_LOGIC_VECTOR(3 downto 0);
	CLOCK,SENZOR_GREUTATE,SENZOR_USA:in STD_LOGIC;
	ENABLE_NUMARATOR:out STD_LOGIC);
end entity TIMER_USA;



architecture F_TIMER_USA of TIMER_USA is

begin

	
	TIMER_DESCHIS:process(CLOCK,REZULTAT_VALIDARE,SENZOR_GREUTATE,SENZOR_USA) 
	variable NUMARATOR:STD_LOGIC_VECTOR(2 downto 0) := "000"; 
	variable ENABLE,ACTIVAT:STD_LOGIC := '0';
	begin
		if REZULTAT_VALIDARE = "01" or REZULTAT_VALIDARE = "10" or SENZOR_GREUTATE = '1' or SENZOR_USA = '1' then
			ACTIVAT := '1';
			NUMARATOR := "000";
			ENABLE := '0';
		end if;
		if ACTIVAT = '1' and ETAJ_MINIM_MAXIM /= "1111" then
			if CLOCK = '1' and CLOCK'EVENT then
				if NUMARATOR = "100" then
					NUMARATOR := "000";
					ACTIVAT := '0';
					ENABLE := '1';
				else
					NUMARATOR := NUMARATOR + '1';
					ENABLE := '0';  
				end if;
			end if;
		elsif REZULTAT_VALIDARE = "10" then
			NUMARATOR := "000";
			ENABLE := '0';
		else
			NUMARATOR := "000";
			ENABLE := '1';
		end if;	
		
		ENABLE_NUMARATOR <= ENABLE;
	
	end process TIMER_DESCHIS;

	
	
end architecture F_TIMER_USA;