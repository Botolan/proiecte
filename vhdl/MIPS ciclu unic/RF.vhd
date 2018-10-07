----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/16/2018 02:56:18 PM
-- Design Name: 
-- Module Name: RF - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity RF is
    Port ( clk : in std_logic;
           ra1 : in std_logic_vector (2 downto 0);
           ra2 : in std_logic_vector (2 downto 0);
           wa : in std_logic_vector (2 downto 0);
           wd : in std_logic_vector (15 downto 0);
           we : in std_logic;
           enable : in std_logic;
           rd1 : out std_logic_vector (15 downto 0);
           rd2 : out std_logic_vector (15 downto 0));
end RF;

architecture Behavioral of RF is
type regfile is array(0 to 7) of STD_LOGIC_VECTOR(15 downto 0);
SIGNAL rf : regfile := (x"0000",x"0011",x"0111",x"1111",x"1110",x"1100",x"1000",x"0000");
begin

    process(clk)
    begin
        if rising_edge(clk) then
            if enable = '1' then
                if we = '1' then
                    rf(conv_integer(wa)) <= wd;     
                end if;
            end if;
        end if;    
    end process;
    
    rd1 <= rf(conv_integer(ra1));
    rd2 <= rf(conv_integer(ra2));

end Behavioral;
