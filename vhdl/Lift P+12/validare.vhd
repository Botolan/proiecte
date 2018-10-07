library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;
--use IEEE.STD_LOGIC_ARITH.all;
use ieee.numeric_std.all;


entity VALIDARE is
	port(ETAJ_CURENT,ETAJ_MINIM_MAXIM:in STD_LOGIC_VECTOR(3 downto 0); 
	INFORMATIE_IN:in STD_LOGIC_VECTOR(1 downto 0);		
	SENS,CLOCK:in STD_LOGIC;	  
	DELETE:out STD_LOGIC;
	ENABLE_TIMER_USA:out STD_LOGIC_VECTOR(1 downto 0));
end entity VALIDARE;



architecture F_VALIDARE of VALIDARE is

begin
	process(CLOCK)
	begin
		if (INFORMATIE_IN = "11" and SENS = '1') or (INFORMATIE_IN = "10" and SENS = '0') or (ETAJ_MINIM_MAXIM = ETAJ_CURENT) then
			ENABLE_TIMER_USA <= "01";
		elsif ETAJ_MINIM_MAXIM = "1111" then
			ENABLE_TIMER_USA <= "10";
		else
			ENABLE_TIMER_USA <= "11";
		end if;
	end process;
	
	DELETE <= '1' when (INFORMATIE_IN = "11" and SENS = '1') or (INFORMATIE_IN = "10" and SENS = '0') or (ETAJ_MINIM_MAXIM = ETAJ_CURENT) else
	'0';
	
		


end architecture F_VALIDARE;


