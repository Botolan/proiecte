library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;
--use IEEE.STD_LOGIC_ARITH.all;
use ieee.numeric_std.all;



entity INPUT is
	port(ETAJ,ETAJ_CURENT:in STD_LOGIC_VECTOR(3 downto 0);
	SUS,JOS,INTERIOR:in STD_LOGIC;	
	WE:out STD_LOGIC;
	ETAJ_OUT:out STD_LOGIC_VECTOR(3 downto 0);
	INFORMATIE_OUT:out STD_LOGIC_VECTOR(1 downto 0));
end entity INPUT;




architecture F_INPUT of INPUT is


begin
	
	ETAJ_OUT <= ETAJ;
	
	INFORMATIE:process(SUS,JOS,INTERIOR)
	variable INFO:STD_LOGIC_VECTOR(1 downto 0) := "00";
	begin
	   if SUS = '1' then
	       INFO := "11";
	   elsif JOS = '1' then
	       INFO := "10";
	   elsif INTERIOR = '1' and ETAJ_CURENT < ETAJ then
	       INFO := "11";
	   elsif INTERIOR = '1' and ETAJ_CURENT > ETAJ then
	       INFO := "10";
       end if;
	   INFORMATIE_OUT <= INFO;
	end process INFORMATIE;
	       
	   
	
	WE <= 
	'1' when INTERIOR = '1' or SUS = '1' or JOS = '1' else
	'0';
		
	
	
	
	
	
end architecture F_INPUT;