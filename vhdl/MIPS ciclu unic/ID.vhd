library IEEE;
use IEEE.STD_LOGIC_1164.ALL;



entity ID is
    Port(clk, enable: in STD_LOGIC;
         Instruction : in STD_LOGIC_VECTOR(15 downto 0);
         WD: in STD_LOGIC_VECTOR(15 downto 0);
         RegWrite, RegDest, ExtOp : in STD_LOGIC;
         RS: out STD_LOGIC_VECTOR(15 downto 0);
         RT: out STD_LOGIC_VECTOR(15 downto 0);
         Ext_Imm: out STD_LOGIC_VECTOR(15 downto 0);
         func: out STD_LOGIC_VECTOR(2 downto 0);
         sa: out STD_LOGIC);
end ID;

architecture Behavioral of ID is
SIGNAL mux: STD_LOGIC_VECTOR(2 downto 0);
begin
    mux <= Instruction(9 downto 7) when RegDest = '0' else
           Instruction(6 downto 4) when RegDest = '1';

    RF:entity work.RF port map(clk => clk, ra1 => Instruction(12 downto 10), ra2 => Instruction(9 downto 7), wa => mux, wd => WD, we => RegWrite, rd1 => rs, rd2 => rt, enable => enable);
    
    func <= instruction(2 downto 0);
    
    process(ExtOp, Instruction(6 downto 0))
    begin
        if ExtOp = '0' then
            Ext_Imm <= "000000000" & Instruction(6 downto 0);
        else if ExtOp = '1' then
                if Instruction(6) = '1' then
                    Ext_Imm <= "111111111" & Instruction(6 downto 0);
                else if Instruction(6) = '0' then
                        Ext_Imm <= "000000000" & Instruction(6 downto 0);
                    end if;
                end if;
            end if;
        end if;
    end process;
    
end Behavioral;
