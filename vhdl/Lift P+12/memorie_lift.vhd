library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.STD_LOGIC_UNSIGNED.all;
--use IEEE.STD_LOGIC_ARITH.all;
use ieee.numeric_std.all;




entity MEMORIE is
	port(WE,ENABLE_DELETE,CLOCK:in STD_LOGIC;	   	
	ETAJ_ADAUGARE,ETAJ_CURENT:in STD_LOGIC_VECTOR(3 downto 0);
	INFORMATIE_INPUT:in STD_LOGIC_VECTOR(1 downto 0); 
	INFORMATIE_OUT:out STD_LOGIC_VECTOR(1 downto 0);
	ETAJ_MINIM_MAXIM_OUT:out STD_LOGIC_VECTOR(3 downto 0);
	SENS_OUT:out STD_LOGIC);
end entity MEMORIE;




architecture F_MEMORIE of MEMORIE is
type MEMORIE_LIFT is array (12 downto 0) of STD_LOGIC_VECTOR(1 downto 0);

signal MEM:MEMORIE_LIFT := (others => (others => '0'));
signal SENS:STD_LOGIC := '1';


begin
	
	SCRIERE_STERGERE:process(WE,ENABLE_DELETE)
	begin
		if WE = '1' and INFORMATIE_INPUT /= "00" then
			MEM(to_integer(unsigned(ETAJ_ADAUGARE(3 downto 0)))) <= INFORMATIE_INPUT;
		end if;
		
		if ENABLE_DELETE = '1' then
			MEM(to_integer(unsigned(ETAJ_CURENT(3 downto 0)))) <= "00"; 
		end if;
	end process SCRIERE_STERGERE;
	
	INFORMATIE_OUT <= MEM(to_integer(unsigned(ETAJ_CURENT(3 downto 0))));
	
	
	MINIM_MAXIM_MAXIM:process(CLOCK,ETAJ_CURENT) 
	variable ETAJ_MINIM_MAXIM:STD_LOGIC_VECTOR(3 downto 0) := "1111";
	variable SENS_TEMP:STD_LOGIC := '1';
	begin
	    if ((ETAJ_MINIM_MAXIM = ETAJ_CURENT) and (MEM(to_integer(unsigned(ETAJ_CURENT(3 downto 0)))) /= "00")) then
            ETAJ_MINIM_MAXIM := ETAJ_CURENT;
        else
            case (SENS) is
                when '0' =>
                if MEM(0) /= "00" then
                    ETAJ_MINIM_MAXIM := "0000";	
                elsif MEM(1) /= "00" then
                    ETAJ_MINIM_MAXIM := "0001";
                elsif MEM(2) /= "00" then
                    ETAJ_MINIM_MAXIM := "0010";
                elsif MEM(3) /= "00" then
                    ETAJ_MINIM_MAXIM := "0011";
                elsif MEM(4) /= "00" then
                    ETAJ_MINIM_MAXIM := "0100";
                elsif MEM(5) /= "00" then
                    ETAJ_MINIM_MAXIM := "0101";
                elsif MEM(6) /= "00" then
                    ETAJ_MINIM_MAXIM := "0110";
                elsif MEM(7) /= "00" then
                    ETAJ_MINIM_MAXIM := "0111";
                elsif MEM(8) /= "00" then
                    ETAJ_MINIM_MAXIM := "1000";
                elsif MEM(9) /= "00" then
                    ETAJ_MINIM_MAXIM := "1001";
                elsif MEM(10) /= "00" then
                    ETAJ_MINIM_MAXIM := "1010";
                elsif MEM(11) /= "00" then
                    ETAJ_MINIM_MAXIM := "1011";
                elsif MEM(12) /= "00" then
                    ETAJ_MINIM_MAXIM := "1100";
                else
                    ETAJ_MINIM_MAXIM := "1111";
                end if;
                when '1' =>
                if MEM(12) /= "00" then
                    ETAJ_MINIM_MAXIM := "1100";	
                elsif MEM(11) /= "00" then
                    ETAJ_MINIM_MAXIM := "1011";
                elsif MEM(10) /= "00" then
                    ETAJ_MINIM_MAXIM := "1010";
                elsif MEM(9) /= "00" then
                    ETAJ_MINIM_MAXIM := "1001";
                elsif MEM(8) /= "00" then
                    ETAJ_MINIM_MAXIM := "1000";
                elsif MEM(7) /= "00" then
                    ETAJ_MINIM_MAXIM := "0111";
                elsif MEM(6) /= "00" then
                    ETAJ_MINIM_MAXIM := "0110";
                elsif MEM(5) /= "00" then
                    ETAJ_MINIM_MAXIM := "0101";
                elsif MEM(4) /= "00" then
                    ETAJ_MINIM_MAXIM := "0100";
                elsif MEM(3) /= "00" then
                    ETAJ_MINIM_MAXIM := "0011";
                elsif MEM(2) /= "00" then
                    ETAJ_MINIM_MAXIM := "0010";
                elsif MEM(1) /= "00" then
                    ETAJ_MINIM_MAXIM := "0001";
                elsif MEM(0) /= "00" then
                    ETAJ_MINIM_MAXIM := "0000";
                else
                    ETAJ_MINIM_MAXIM := "1111";
                end if;	
                when others =>
            end case;
            if ETAJ_MINIM_MAXIM = "1111" then
                SENS_TEMP := not(SENS_TEMP);
            elsif ETAJ_MINIM_MAXIM < ETAJ_CURENT then
                SENS_TEMP := '0';
            else
                SENS_TEMP := '1';
            end if;
        end if;
        SENS <= SENS_TEMP;
        ETAJ_MINIM_MAXIM_OUT <= ETAJ_MINIM_MAXIM;
	end process MINIM_MAXIM_MAXIM;
	
	SENS_OUT <= SENS;
				
			
	
end architecture F_MEMORIE;


