library IEEE;
use IEEE.STD_LOGIC_1164.all;
USE ieee.numeric_std.ALL;


entity decodificator is
	port(A:in STD_LOGIC_VECTOR(3 downto 0);
	CIFRA1:out STD_LOGIC_VECTOR(3 downto 0);
	CIFRA2:out STD_LOGIC_VECTOR(3 downto 0));
end entity decodificator;

architecture F_DECODIFICATOR of DECODIFICATOR is
begin 
	CIFRA_1:process(A)
	variable AUX:integer;
	begin
		AUX := to_integer(unsigned(A(3 downto 0)));
		if AUX > 9 then
			CIFRA1 <= "0001";
		else
			CIFRA1 <= "0000";
		end if;
	end process CIFRA_1;
	
	CIFRA_2:process(A)
	variable AUX:integer;
	begin
		AUX := to_integer(unsigned(A(3 downto 0)));
		if AUX > 9 then
			CIFRA2 <= STD_LOGIC_VECTOR(to_unsigned((AUX mod 10),CIFRA2'LENGTH));
		else
		    CIFRA2 <= STD_LOGIC_VECTOR(to_unsigned(AUX,CIFRA2'LENGTH));
		end if;
	end process CIFRA_2;
end architecture F_DECODIFICATOR;

		